package com.shabbir.Jwt_Impl.Controller;

import com.shabbir.Jwt_Impl.Entity.JournalEntry;
import com.shabbir.Jwt_Impl.Entity.User;
import com.shabbir.Jwt_Impl.Service.SearchingService;
import com.shabbir.Jwt_Impl.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    SearchingService searchingService;


    @GetMapping
    public ResponseEntity<List<JournalEntry>> getFilteredList(@RequestParam String title){
        return searchingService.filteredEntry(title);
    }

}
