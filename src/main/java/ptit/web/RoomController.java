package ptit.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;
import ptit.Room;
import ptit.data.RoomRepository;

@Slf4j
@Controller
@RequestMapping("/room")
public class RoomController {
    private final RoomRepository roomRepo;

    @Autowired
    public RoomController(RoomRepository roomRepo) {
        this.roomRepo = roomRepo;
    }

    @GetMapping
    public String showRoomMgmt(Model model) {
        // model.addAttribute("taco", new Taco());
        model.addAttribute("page", "Quản lý phòng trọ");
        return "QLPT";
    }

    @GetMapping("/addRoom")
    public String showAddRoom(ServletRequest request, Model model) {
        model.addAttribute("add", new Room());
        model.addAttribute("page", "Thêm phòng");
        Map<String, String[]> paramMap = request.getParameterMap();

        if (paramMap.containsKey("error")) {
            model.addAttribute("msg", "Có lỗi xảy ra (Trùng thông tin)");
        } else if (paramMap.containsKey("formatError")) {
            model.addAttribute("msg", "Có lỗi xảy ra (Sai định dạng)");
        }
        return "addRoom";
    }

    @GetMapping("/roomSearch")
    public String showRoomSearch(ServletRequest request, Model model) {
        model.addAttribute("page", "Tìm kiếm phòng");
        List<Room> rooms = (List<Room>) roomRepo.findAll();
        model.addAttribute("rooms", rooms);
        model.addAttribute("search", new Room());
        Map<String, String[]> paramMap = request.getParameterMap();
        if (paramMap.containsKey("error")) {
            model.addAttribute("msg", "Có lỗi xảy ra (Phòng có sinh viên đang ở)!");
        } else if (paramMap.containsKey("delete")) {
            model.addAttribute("msg", "Xóa phòng thành công!");
        } else if (paramMap.containsKey("edit")) {
            model.addAttribute("msg", "Thay đổi thông tin thành công!");
        } else if (paramMap.containsKey("add")) {
            model.addAttribute("msg", "Thêm phòng thành công!");
        } else if (paramMap.containsKey("formatError")) {
            model.addAttribute("msg", "Sai định dạng thông tin!");
        }
        return "roomSearch";
    }

    @GetMapping("/roomEdit")
    // @RequestMapping("/roomEdit")
    public String showRoomEdit(Model model, @RequestParam(name = "id") Long id) {
        model.addAttribute("page", "Sửa phòng");
        Room room = roomRepo.findById(id).get();
        model.addAttribute("room", room);
        return "editRoom";
    }

    @GetMapping("/delete")
    // @RequestMapping("/roomEdit")
    public String deleteRoom(Model model, @RequestParam(name = "id") Long id) {
        try {
            roomRepo.deleteById(id);
        } catch (Exception e) {
            List<Room> rooms = (List<Room>) roomRepo.findAll();
            model.addAttribute("rooms", rooms);
            model.addAttribute("search", new Room());
            return "redirect:/room/roomSearch?error";
        }
        List<Room> rooms = (List<Room>) roomRepo.findAll();
        model.addAttribute("rooms", rooms);
        model.addAttribute("search", new Room());
        return "redirect:/room/roomSearch?delete";
    }

    @PostMapping("/roomSearch")
    public String processSearch(Room room, Model model) {
        List<Room> allRooms = (List<Room>) roomRepo.findAll();
        List<Room> rooms = new ArrayList<>();
        for (Room r : allRooms) {
            if (r.getPrice() <= room.getPrice()) {
                rooms.add(r);
            }
        }
        model.addAttribute("rooms", rooms);
        model.addAttribute("search", new Room());
        model.addAttribute("page", "Tìm kiếm phòng");
        return "roomSearch";
    }

    @PostMapping("/roomEdit")
    public String processEdit(Room room, Model model) {
        Boolean valid = true;
        if (room.getPrice() <= 0 || room.getAmountPeople() <= 0) {
            valid = false;
        }
        if (valid == true) {
            Room roomB4 = roomRepo.findById(room.getId()).get();
            roomB4.setPrice(room.getPrice());
            roomB4.setType(room.getType());
            roomB4.setAmountPeople(room.getAmountPeople());
            roomB4.setRoomNumber(room.getRoomNumber());
            try {
                roomRepo.save(roomB4);
            } catch (Exception e) {
                return "redirect:/room/roomSearch?error";
            }
            return "redirect:/room/roomSearch?edit";
        } else {
            return "redirect:/room/roomSearch?formatError";
        }

    }

    @PostMapping("/addRoom")
    public String processAdd(Room room) {
        Boolean valid = true;
        if (room.getPrice() <= 0 || room.getAmountPeople() <= 0) {
            valid = false;
        }
        if (valid == true) {
            try {
                roomRepo.save(room);
            } catch (Exception e) {
                return "redirect:/room/addRoom?error";
            }
            return "redirect:/room/roomSearch?add";
        } else {
            return "redirect:/room/addRoom?formatError";
        }
    }

}