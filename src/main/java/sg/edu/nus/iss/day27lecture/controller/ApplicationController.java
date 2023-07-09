package sg.edu.nus.iss.day27lecture.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import sg.edu.nus.iss.day27lecture.model.Comment;
import sg.edu.nus.iss.day27lecture.repository.ApplicationRepository;

@Controller
@RequestMapping
public class ApplicationController {

    @Autowired
    private ApplicationRepository repository;

    @GetMapping
    public String indexPage() {
        return "index";
    }

    @GetMapping("/search")
    public String searchBoardGamePage(@RequestParam String game, Model model) {
        model.addAttribute("query", game);
        model.addAttribute("gamesList", repository.retrieveSearchResultsByName(game));
        return "search";
    }

    @GetMapping("/game/{gameID}")
    public ModelAndView showBoardGamePage(@PathVariable Integer gameID) {
        ModelAndView mav = new ModelAndView();

        if (!repository.checkIfBoardGameExistsByID(gameID)) {
            mav.setStatus(HttpStatus.NOT_FOUND);
            mav.setViewName("404");
            return mav;
        }

        mav.addObject("game", repository.getGameByID(gameID));
        mav.addObject("comments", repository.getCommentsByGameID(repository.getGameByID(gameID).getGid()));
        mav.setViewName("game");
        mav.setStatus(HttpStatus.OK);
        return mav;
    }

    @PostMapping("/comment")
    public String addNewComment(@RequestBody MultiValueMap<String, String> formInputs) {

        Integer gid = Integer.parseInt(formInputs.getFirst("gid"));
		String user = formInputs.getFirst("user");
		Integer rating = Integer.parseInt(formInputs.getFirst("rating"));
		String text = formInputs.getFirst("c_text");

		Comment comment = new Comment("", user, rating, text, gid);
        repository.insertComment(comment);
        return "success";
    }

}
