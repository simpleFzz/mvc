package com.fzz.webui.controller;

import com.fzz.data.dao.ActorRepository;
import com.fzz.data.dao.MovieRepository;
import com.fzz.data.entity.Actor;
import com.fzz.data.entity.Movie;
import com.fzz.data.service.PageService;
import org.apache.commons.lang3.StringUtils;
import org.neo4j.ogm.cypher.ComparisonOperator;
import org.neo4j.ogm.cypher.Filter;
import org.neo4j.ogm.cypher.Filters;
import org.neo4j.ogm.cypher.function.FilterFunction;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


/**
 * @ClassName MovieController
 * @Description
 * @Author fzz
 * @Date 2018/11/6
 **/
@Controller
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private PageService<Movie> pagesService;

    private static Logger logger = LoggerFactory.getLogger(MovieController.class);

    @RequestMapping("new")
    public ModelAndView create(ModelMap modelMap) {

        String[] files = {"/images/movie/xiyouji.jpg", "/images/movie/xiyoujinext.jpg"};
        modelMap.addAttribute("files", files);
        return new ModelAndView("movie/new");
    }

    @PostMapping("/save")
    public String save(Movie movie) {
        movieRepository.save(movie);
        logger.info("新增 -> ID={}", movie.getId());
        return "1";
    }

    @RequestMapping("/{id}")
    public ModelAndView show(ModelMap modelMap, @PathVariable("id") Long id) {
        Optional<Movie> findById = movieRepository.findById(id);
        modelMap.addAttribute("movie", findById.get());
        return new ModelAndView("movie/show");
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView update(ModelMap modelMap, @PathVariable("id") Long id) {
        Optional<Movie> getById = movieRepository.findById(id);

        Movie movie = getById.get();
        String[] files = {"/images/movie/xiyouji.jpg", "/images/movie/xiyoujinext.jpg"};
        String[] roleList = {"唐僧", "孙悟空", "沙僧", "猪八戒"};
        Iterable<Actor> actors = actorRepository.findAll();

        modelMap.addAttribute("files", files);
        modelMap.addAttribute("rolelist", roleList);
        modelMap.addAttribute("movie", movie);
        modelMap.addAttribute("actors", actors);
        return new ModelAndView("movie/edit");

    }


    @RequestMapping(method = RequestMethod.POST, value = "/update")
    public String update(Movie movie, HttpServletRequest request) throws Exception {
        String rolename = request.getParameter("rolename");
        String actorid = request.getParameter("actorid");
        Optional<Movie> byId = movieRepository.findById(movie.getId());
        Movie old = byId.get();
        old.setName(movie.getName());
        old.setPhoto(movie.getPhoto());
        old.setCreateDate(movie.getCreateDate());
        if (!StringUtils.isEmpty(rolename) && !StringUtils.isEmpty(actorid)) {
            Optional<Actor> actor = actorRepository.findById(new Long(actorid));
            old.addRole(actor.get(), rolename);
        }
        movieRepository.save(old);
        logger.info("修改->ID=" + old.getId());
        return "1";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable Long id) throws Exception {
        Optional<Movie> movie = movieRepository.findById(id);
        movieRepository.delete(movie.get());
        logger.info("删除->ID=" + id);
        return "1";
    }

    @RequestMapping(value = "/1ist")
    public Page list(HttpServletRequest request) throws Exception {
        String name = request.getParameter("name");
        String page = request.getParameter("page");
        String size = request.getParameter("size");
        Pageable pageable = new PageRequest(page == null ? 0 : Integer.parseInt(page), size == null ? 10 : Integer.parseInt(size),new Sort(Sort.Direction.DESC, "id"));
        Filters filters = new Filters();
        if (!StringUtils.isEmpty(name)) {

        }
        return pagesService.findAll(Movie.class, pageable, filters);
    }
}