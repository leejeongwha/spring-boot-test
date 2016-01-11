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
import com.naver.test.school.model.Subject;
import com.naver.test.school.repository.GradeRepository;
import com.naver.test.school.repository.SchoolUserRepository;
import com.naver.test.school.repository.SubjectRepository;

@Controller
@RequestMapping("school")
public class SubjectController {
	private static final Logger logger = LoggerFactory.getLogger(SubjectController.class);

	@Autowired
	private SubjectRepository subjectRepository;

	@Autowired
	private GradeRepository gradeRepository;

	@Autowired
	private SchoolUserRepository schoolUserRepository;

	/**
	 * 과목 리스트
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("subjects")
	public String subjects(Model model) {
		List<Subject> subjectList = subjectRepository.findAll();
		model.addAttribute("subjectList", subjectList);
		return "school/subjects";
	}

	/**
	 * 과목 등록 폼
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("subjects/form")
	public String subjectsForm(Model model) {
		return "school/subjectForm";
	}

	/**
	 * 과목 상세
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("subjects/{id}")
	public String getSubject(Model model, @PathVariable int id) {
		Subject subject = subjectRepository.findOne(id);
		model.addAttribute("subject", subject);
		return "school/subjectForm";
	}

	/**
	 * 과목 저장
	 * 
	 * @param subject
	 * @return
	 */
	@RequestMapping("subjects/save")
	public String save(Subject subject) {
		subjectRepository.save(subject);
		return "redirect:/school/subjects";
	}

	/**
	 * 과목 삭제
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("subjects/delete/{id}")
	@ResponseBody
	public String delete(@PathVariable int id) {
		try {
			subjectRepository.delete(id);
		} catch (Exception e) {
			logger.error("삭제실패 : {}", e.getMessage());
			return "fail";
		}

		return "success";
	}

	/**
	 * 과목별 성적 조회
	 * 
	 * @param model
	 * @param subjectId
	 * @return
	 */
	@RequestMapping("subjects/{subjectId}/grades")
	public String getGradesBySubjects(Model model, @PathVariable int subjectId) {
		List<Grade> gradeList = gradeRepository.findBySubjectId(subjectId);

		model.addAttribute("gradeList", gradeList);
		model.addAttribute("subjectName", subjectRepository.findOne(subjectId).getSubjectName());
		return "school/gradeForm";
	}
}
