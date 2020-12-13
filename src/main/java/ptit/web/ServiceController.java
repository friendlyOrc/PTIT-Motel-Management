package ptit.web;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;
import ptit.Service;
import ptit.ServiceStat;
import ptit.Student;
import ptit.StudentService;
import ptit.data.ServiceRepository;
import ptit.data.StudentRepository;
import ptit.data.StudentServiceRepository;

@Slf4j
@Controller
@RequestMapping("/service")
public class ServiceController {

	private final ServiceRepository serviceRepo;
	private final StudentRepository studentRepo;
	private final StudentServiceRepository stsvRepo;

	@Autowired
	public ServiceController(ServiceRepository serviceRepo, StudentRepository studentRepo,
			StudentServiceRepository stsvRepo) {
		this.serviceRepo = serviceRepo;
		this.studentRepo = studentRepo;
		this.stsvRepo = stsvRepo;
	}

	@GetMapping
	public String ServiceServiceMgmt(Model model) {
		model.addAttribute("page", "Quản lý dịch vụ");
		return "QLDV";
	}

	@GetMapping("/addService")
	public String showAddService(ServletRequest request, Model model) {
		model.addAttribute("add", new Service());
		model.addAttribute("page", "Thêm dịch vụ");
		Map<String, String[]> paramMap = request.getParameterMap();

		if (paramMap.containsKey("error")) {
			model.addAttribute("msg", "Có lỗi xảy ra (Trùng thông tin)");
		} else if (paramMap.containsKey("formatError")) {
			model.addAttribute("msg", "Có lỗi xảy ra (Sai định dạng)");
		}
		return "addService";
	}

	@GetMapping("/serviceFound")
	public String showServiceSearch(ServletRequest request, Model model) {
		model.addAttribute("page", "Tìm kiếm dịch vụ");
		List<Service> services = (List<Service>) serviceRepo.findAll();
		model.addAttribute("services", services);
		model.addAttribute("search", new Service());
		Map<String, String[]> paramMap = request.getParameterMap();
		if (paramMap.containsKey("error")) {
			model.addAttribute("msg", "Có lỗi xảy ra (Dịch vụ có sinh viên sử dụng)!");
		} else if (paramMap.containsKey("delete")) {
			model.addAttribute("msg", "Xóa dịch vụ thành công!");
		} else if (paramMap.containsKey("edit")) {
			model.addAttribute("msg", "Thay đổi thông tin thành công!");
		} else if (paramMap.containsKey("add")) {
			model.addAttribute("msg", "Thêm dịch vụ thành công!");
		} else if (paramMap.containsKey("formatError")) {
			model.addAttribute("msg", "Sai định dạng thông tin!");
		}
		model.addAttribute("hidden2", "hidden");
		model.addAttribute("hidden1", null);
		return "serviceFound";
	}

	@GetMapping("/serviceFound2")
	public String showServiceSearch2(ServletRequest request, Model model) {
		model.addAttribute("page", "Tìm kiếm dịch vụ");
		List<Service> services = (List<Service>) serviceRepo.findAll();
		model.addAttribute("services", services);
		model.addAttribute("search", new Service());
		Map<String, String[]> paramMap = request.getParameterMap();
		if (paramMap.containsKey("error")) {
			model.addAttribute("msg", "Có lỗi xảy ra!");
		}
		model.addAttribute("hidden1", "hidden");
		model.addAttribute("hidden2", null);
		return "serviceFound";
	}

	@GetMapping("/serviceEdit")
	public String showServiceEdit(Model model, @RequestParam(name = "id") Long id) {
		model.addAttribute("page", "Sửa phòng");
		Service service = serviceRepo.findById(id).get();
		model.addAttribute("service", service);
		return "editService";
	}

	@GetMapping("/delete")
	public String deleteService(Model model, @RequestParam(name = "id") Long id) {
		System.out.println("id : " + id);
		try {
			serviceRepo.deleteById(id);
		} catch (Exception e) {
			List<Service> services = (List<Service>) serviceRepo.findAll();
			model.addAttribute("Services", services);
			model.addAttribute("search", new Service());
			return "redirect:/service/serviceFound?error";
		}
		List<Service> services = (List<Service>) serviceRepo.findAll();
		model.addAttribute("services", services);
		model.addAttribute("search", new Service());
		return "redirect:/service/serviceFound?delete";
	}

	@GetMapping("/removeUse")
	public String deletesv(Model model, @RequestParam(name = "idsv") Long idsv,
			@RequestParam(name = "iddv") Long iddv) {

		System.out.println(idsv + "\t" + iddv);
		try {
			StudentService lss = stsvRepo.findByStudentidAndServiceid(idsv, iddv);
			stsvRepo.delete(lss);
		} catch (Exception e) {
			ArrayList<StudentService> stsv = (ArrayList<StudentService>) stsvRepo.findByService_Id(iddv);
			ArrayList<Student> students = new ArrayList<>();
			for (StudentService ss : stsv) {
				students.add(ss.getStudent());
			}
			model.addAttribute("students", students);
			model.addAttribute("search", new Student());
			return "redirect:/service/studentuseservice?id=" + iddv + "?error";
		}
		ArrayList<StudentService> stsv = (ArrayList<StudentService>) stsvRepo.findByService_Id(iddv);
		ArrayList<Student> students = new ArrayList<>();
		for (StudentService ss : stsv) {
			students.add(ss.getStudent());
		}
		model.addAttribute("students", students);
		model.addAttribute("search", new Student());
		return "redirect:/service/studentuseservice?id=" + iddv;
	}

	@PostMapping("/serviceFound")
	public String processSearch(Service service, Model model) {
		List<Service> allServices = (List<Service>) serviceRepo.findAll();
		List<Service> services = new ArrayList<>();
		System.out.print(service.getName());
		for (Service r : allServices) {
			if (r.getName().contains(service.getName()) || r.getName().contains(service.getName().toUpperCase())) {
				services.add(r);
			}
		}
		model.addAttribute("services", services);
		model.addAttribute("page", "Tìm kiếm dịch vụ");
		model.addAttribute("search", new Service());
		return "serviceFound";
	}

	@PostMapping("/serviceFound2")
	public String processSearch2(ServletRequest request, Service service, Model model) {
		Map<String, String[]> paramMap = request.getParameterMap();
		if (paramMap.containsKey("add")) {
			model.addAttribute("msg", "Thêm sinh viên sử dụng thành công!");
		} else if (paramMap.containsKey("error")) {
			model.addAttribute("msg", "Có lỗi xảy ra!");
		}
		List<Service> allServices = (List<Service>) serviceRepo.findAll();
		List<Service> services = new ArrayList<>();
		for (Service r : allServices) {
			if (r.getName().contains(service.getName())) {
				services.add(r);
			}
		}
		model.addAttribute("services", services);
		model.addAttribute("search", new Service());
		model.addAttribute("page", "Tìm kiếm dịch vụ");
		return "serviceFound";
	}

	@PostMapping("/serviceEdit")
	public String processEdit(Service service, Model model) {
		Boolean valid = true;
		if (service.getName() == null || service.getName().equalsIgnoreCase("") || service.getPrice() <= 0) {
			valid = false;
		}
		if (valid == true) {
			Service serviceB4 = serviceRepo.findById(service.getId()).get();
			serviceB4.setName(service.getName());
			serviceB4.setPrice(service.getPrice());
			try {
				serviceRepo.save(serviceB4);
			} catch (Exception e) {
				return "redirect:/service/serviceFound?error";
			}
			return "redirect:/service/serviceFound?edit";
		} else {
			return "redirect:/service/serviceFound?formatError";
		}

	}

	@PostMapping("/addService")
	public String processAdd(Service service) {
		Boolean valid = true;
		if (service.getName() == null || service.getPrice() <= 0) {
			valid = false;
		}
		if (valid == true) {
			try {
				serviceRepo.save(service);
			} catch (Exception e) {
				return "redirect:/service/addService?error";
			}
			return "redirect:/service/serviceFound?add";
		} else {
			return "redirect:/service/addService?formatError";
		}
	}

	@GetMapping("/studentuseservice")
	public String studentuseservice(ServletRequest request, Model model, @RequestParam(name = "id") Long id) {
		Map<String, String[]> paramMap = request.getParameterMap();
		if (paramMap.containsKey("add")) {
			model.addAttribute("msg", "Thêm sinh viên sử dụng thành công!");
		} else if (paramMap.containsKey("error")) {
			model.addAttribute("msg", "Có lỗi xảy ra!");
		}
		model.addAttribute("page", "Sử dụng dịch vụ");
		Service service = serviceRepo.findById(id).get();
		ArrayList<StudentService> stsv = (ArrayList<StudentService>) stsvRepo.findByService_Id(id);
		// ArrayList<Student> students = new ArrayList<>();
		// for (StudentService ss : stsv) {
		// students.add(ss.getStudent());
		// }
		model.addAttribute("ss", stsv);
		model.addAttribute("service", service);

		return "studentUseService";
	}

	@GetMapping("/addServiceUsed")
	public String addServiceUsed(ServletRequest request, Model model) {

		Map<String, String[]> paramMap = request.getParameterMap();
		if (paramMap.containsKey("error")) {
			model.addAttribute("msg", "Có lỗi xảy ra!");
		}
		model.addAttribute("page", "Thêm sinh viên sử dụng");
		ArrayList<Student> studetns = (ArrayList<Student>) studentRepo.findAll();
		ArrayList<Service> services = (ArrayList<Service>) serviceRepo.findAll();
		model.addAttribute("services", services);
		model.addAttribute("students", studetns);
		model.addAttribute("ss", new StudentService());

		return "addServiceUsed";
	}

	@PostMapping("/addServiceUsed")
	public String postAddServiceUsed(StudentService ss) {
		System.out.print(ss.getService().getName() + ss.getStudent().getStudentName() + "\n");
		try {

			Calendar calendar = Calendar.getInstance();
			Date date = new Date(calendar.getTime().getTime());
			ss.setDate(date);
			stsvRepo.save(ss);
		} catch (Exception e) {
			return "redirect:/service/addServiceUsed?error";
		}
		return "redirect:/service/serviceFound2?add";
	}

	@GetMapping("/serviceStat")
	public String serviceStat(ServletRequest request, Model model) {

		Map<String, String[]> paramMap = request.getParameterMap();
		if (paramMap.containsKey("error")) {
			model.addAttribute("msg", "Có lỗi xảy ra!");
		}
		model.addAttribute("page", "Thống kê dịch vụ");
		model.addAttribute("ss", new ArrayList<StudentService>());
		model.addAttribute("ServiceStat", new ServiceStat());

		return "serviceStat";
	}

	@PostMapping("/serviceStat")
	public String postServiceStat(ServiceStat serviceStat, Model model) {

		int m = serviceStat.getMonth();
		ArrayList<ServiceStat> rs = new ArrayList<>();
		ArrayList<Service> services = (ArrayList<Service>) serviceRepo.findAll();

		for (Service sv : services) {
			ServiceStat temp = new ServiceStat();
			temp.setService(sv);
			temp.setMonth(m);
			ArrayList<StudentService> ss = stsvRepo.stat(sv.getId(), m);
			temp.setSs(ss);
			Float total = (float) 0;
			int count = 0;
			for (StudentService tempss : ss) {
				total += tempss.getQuantity() * tempss.getService().getPrice();
				count += tempss.getQuantity();
			}
			temp.setTotal(total);
			temp.setCount(count);
			rs.add(temp);
		}
		model.addAttribute("page", "Thống kê dịch vụ");
		model.addAttribute("ss", rs);
		model.addAttribute("ServiceStat", serviceStat);

		return "serviceStat";
	}
}
