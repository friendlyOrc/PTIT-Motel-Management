package ptit.web;

import java.util.ArrayList;
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
			return "redirect:/service/studentuseservice?error";
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
	public String processSearch2(Service service, Model model) {
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
	public String studentuseservice(Model model, @RequestParam(name = "id") Long id) {

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

}
