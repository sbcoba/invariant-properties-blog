/*
 * This code was written by Bear Giles <bgiles@coyotesong.com> and he
 * licenses this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Any contributions made by others are licensed to this project under
 * one or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * 
 * Copyright (c) 2013 Bear Giles <bgiles@coyotesong.com>
 */
package com.invariantproperties.sandbox.student.business;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import com.invariantproperties.sandbox.student.domain.Student;
import com.invariantproperties.sandbox.student.repository.StudentRepository;

public class StudentServiceImpl implements StudentService {
    private static final Logger log = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Resource
    private StudentRepository studentRepository;

    /**
     * @see com.invariantproperties.sandbox.student.business.StudentService#
     *      findAllStudents()
     */
    @Transactional(readOnly = true)
    @Override
    public List<Student> findAllStudents() {
        final List<Student> students = studentRepository.findAll();
        return students;
    }

    /**
     * @see com.invariantproperties.sandbox.student.business.StudentService#
     *      findStudentById(java.lang.Integer)
     */
    @Transactional(readOnly = true)
    @Override
    public Student findStudentById(Integer id) {
        Student student = null;
        try {
            student = studentRepository.findOne(id);
        } catch (DataAccessException e) {
            log.info("internal error retrieving student: " + id);

            return null;
        }

        if (student == null) {
            log.debug("did not find student: " + id);
            return null;
        }

        return student;
    }

    /**
     * @see com.invariantproperties.sandbox.student.business.StudentService#
     *      findStudentByUuid(java.lang.String)
     */
    @Transactional(readOnly = true)
    @Override
    public Student findStudentByUuid(String uuid) {
        Student student = null;
        try {
            student = studentRepository.findStudentByUuid(uuid);
        } catch (DataAccessException e) {
            log.info("internal error retrieving student: " + uuid);

            return null;
        }

        if (student == null) {
            log.debug("did not find student: " + uuid);
            return null;
        }

        return student;
    }

    /**
     * @see com.invariantproperties.sandbox.student.business.StudentService#
     *      findStudentByEmail(java.lang.String)
     */
    @Transactional(readOnly = true)
    @Override
    public Student findStudentByEmailAddress(String emailAddress) {
        Student student = null;
        try {
            student = studentRepository.findStudentByEmailAddress(emailAddress);
        } catch (DataAccessException e) {
            log.info("internal error retrieving student: " + emailAddress);

            return null;
        }

        if (student == null) {
            log.debug("did not find student: " + emailAddress);
            return null;
        }

        return student;
    }

    /**
     * @see com.invariantproperties.sandbox.student.business.StudentService#
     *      createStudent(java.lang.String, java.lang.String)
     */
    @Transactional
    @Override
    public Student createStudent(String name, String emailAddress) {
        final Student student = new Student();
        student.setName(name);
        student.setEmailAddress(emailAddress);
        final Student actual = studentRepository.saveAndFlush(student);

        return actual;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.invariantproperties.sandbox.student.persistence.StudentService#
     * updateStudent(com.invariantproperties.sandbox.student.domain.Student,
     * java.lang.String, java.lang.String)
     */
    public Student updateStudent(Student student, String name, String emailAddress) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see com.invariantproperties.sandbox.student.business.StudentService#
     *      deleteStudent(java.lang.String)
     */
    @Transactional
    @Override
    public void deleteStudent(String uuid) {
        Student student = null;
        try {
            student = studentRepository.findStudentByUuid(uuid);
        } catch (DataAccessException e) {
            log.info("internal error retrieving student: " + uuid);
            throw new ObjectNotFoundException(uuid);
        }

        if (student == null) {
            log.debug("did not find student: " + uuid);
            throw new ObjectNotFoundException(uuid);
        }

        studentRepository.delete(student);
    }
}