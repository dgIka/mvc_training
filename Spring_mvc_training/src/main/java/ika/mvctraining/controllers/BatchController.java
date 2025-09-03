//package ika.mvctraining.controllers;
//
//
//import ika.mvctraining.dao.PersonDAO;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//
//@Controller
//@RequestMapping("/test-batch-update")
//public class BatchController {
//
//    private final PersonDAO personDAO;
//
//    public BatchController(PersonDAO personDAO) {
//        this.personDAO = personDAO;
//    }
//
//    @GetMapping
//    public String index() {
//        return "batch/index";
//    }
//
//    @GetMapping("/without")
//    public String withoutBatch() {
//        personDAO.testMultipleUpdate();
//        return "redirect:/people";
//    }
//
//    @GetMapping("/with")
//    public String withBatch() {
//        personDAO.testBatchUpdate();
//        return "redirect:/people";
//    }
//
//    @DeleteMapping
//    public String delete(@RequestParam("age") int age) {
//        personDAO.testBatchDelete(age);
//        return "redirect:/people";
//    }
//
//    @GetMapping("/delete")
//    public String deleteBatch(Model model) {
//        model.addAttribute("age", 0);
//        return "batch/delete";
//    }
//
//
//
//
//}
