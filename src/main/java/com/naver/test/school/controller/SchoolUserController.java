package com.naver.test.school.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.naver.test.school.model.Grade;
import com.naver.test.school.model.Role;
import com.naver.test.school.model.SchoolUser;
import com.naver.test.school.model.Subject;
import com.naver.test.school.repository.SchoolUserRepository;
import com.naver.test.school.repository.SubjectRepository;

@Controller
@RequestMapping("school")
public class SchoolUserController {
	private static final Logger logger = LoggerFactory.getLogger(SchoolUserController.class);

	@Autowired
	private SchoolUserRepository schoolUserRepository;

	@Autowired
	private SubjectRepository subjectRepository;

	@RequestMapping({ "/", "" })
	public String index() {
		return "school/index";
	}

	/**
	 * 인원 리스트
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("users")
	public String users(Model model) {
		List<SchoolUser> schoolUserList = schoolUserRepository.findAll();
		model.addAttribute("userList", schoolUserList);
		return "school/users";
	}

	/**
	 * 인원 등록
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("users/form")
	public String usersForm(Model model) {
		model.addAttribute("subjectList", subjectRepository.findAll());
		return "school/userForm";
	}

	/**
	 * 인원 상세
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("users/{id}")
	public String getSchoolUser(Model model, @PathVariable int id) {
		SchoolUser schoolUser = schoolUserRepository.findOne(id);

		// test
		List<Grade> grades = schoolUser.getGrades();
		for (Grade grade : grades) {
			logger.info("과목 : " + grade.getSubject().getSubjectName());
			logger.info("성적 : " + grade.getScore());
		}

		model.addAttribute("user", schoolUser);
		model.addAttribute("subjectList", subjectRepository.findAll());

		return "school/userForm";
	}

	/**
	 * 인원 저장
	 * 
	 * @param schoolUser
	 * @return
	 */
	@RequestMapping("users/save")
	public String save(SchoolUser schoolUser) {
		if (schoolUser.getRole() != Role.TEACHER) {
			schoolUser.setSubjectId(null);
		}

		schoolUserRepository.save(schoolUser);
		return "redirect:/school/users";
	}

	/**
	 * 인원 삭제
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("users/delete/{id}")
	@ResponseBody
	public String delete(@PathVariable int id) {
		try {
			schoolUserRepository.delete(id);
		} catch (Exception e) {
			return "fail";
		}

		return "success";
	}

	@RequestMapping("users/{id}/subject")
	@ResponseBody
	public Subject getSubject(@PathVariable int id) {
		SchoolUser user = schoolUserRepository.findOne(id);

		if (user.getSubjectId() != null) {
			return subjectRepository.findOne(user.getSubjectId());
		} else {
			return new Subject();
		}
	}

	@RequestMapping("users/{id}/grades")
	@ResponseBody
	public List<Grade> getGrades(@PathVariable int id) {
		SchoolUser user = schoolUserRepository.findOne(id);

		return user.getGrades();
	}
}
