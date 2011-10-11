/**
 * Copyright (c) 2010 Anthony W. Juckel
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Anthony W. Juckel - initial API and implementation
 * 
 */
package net.juckel.rcp.databinding.aggregate.tests.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains
 * accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * 
 * @see net.juckel.rcp.databinding.aggregate.tests.model.ClassroomFactory
 * @model kind="package"
 * @generated
 */
public interface ClassroomPackage extends EPackage {
    /**
     * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    String eNAME = "model";

    /**
     * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    String eNS_URI = "http://net.juckel/rcp/databinding/tests/";

    /**
     * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    String eNS_PREFIX = "classroom";

    /**
     * The singleton instance of the package. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     */
    ClassroomPackage eINSTANCE = net.juckel.rcp.databinding.aggregate.tests.model.impl.ClassroomPackageImpl
            .init();

    /**
     * The meta object id for the '
     * {@link net.juckel.rcp.databinding.aggregate.tests.model.impl.StudentImpl
     * <em>Student</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see net.juckel.rcp.databinding.aggregate.tests.model.impl.StudentImpl
     * @see net.juckel.rcp.databinding.aggregate.tests.model.impl.ClassroomPackageImpl#getStudent()
     * @generated
     */
    int STUDENT = 0;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int STUDENT__NAME = 0;

    /**
     * The feature id for the '<em><b>Test Results</b></em>' reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int STUDENT__TEST_RESULTS = 1;

    /**
     * The number of structural features of the '<em>Student</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int STUDENT_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '
     * {@link net.juckel.rcp.databinding.aggregate.tests.model.impl.ExamResultImpl
     * <em>Exam Result</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     * 
     * @see net.juckel.rcp.databinding.aggregate.tests.model.impl.ExamResultImpl
     * @see net.juckel.rcp.databinding.aggregate.tests.model.impl.ClassroomPackageImpl#getExamResult()
     * @generated
     */
    int EXAM_RESULT = 1;

    /**
     * The feature id for the '<em><b>Score</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int EXAM_RESULT__SCORE = 0;

    /**
     * The feature id for the '<em><b>Student</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int EXAM_RESULT__STUDENT = 1;

    /**
     * The feature id for the '<em><b>Weight</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int EXAM_RESULT__WEIGHT = 2;

    /**
     * The number of structural features of the '<em>Exam Result</em>' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int EXAM_RESULT_FEATURE_COUNT = 3;

    /**
     * Returns the meta object for class '
     * {@link net.juckel.rcp.databinding.aggregate.tests.model.Student
     * <em>Student</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for class '<em>Student</em>'.
     * @see net.juckel.rcp.databinding.aggregate.tests.model.Student
     * @generated
     */
    EClass getStudent();

    /**
     * Returns the meta object for the attribute '
     * {@link net.juckel.rcp.databinding.aggregate.tests.model.Student#getName
     * <em>Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see net.juckel.rcp.databinding.aggregate.tests.model.Student#getName()
     * @see #getStudent()
     * @generated
     */
    EAttribute getStudent_Name();

    /**
     * Returns the meta object for the reference list '
     * {@link net.juckel.rcp.databinding.aggregate.tests.model.Student#getTestResults
     * <em>Test Results</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for the reference list '<em>Test Results</em>'.
     * @see net.juckel.rcp.databinding.aggregate.tests.model.Student#getTestResults()
     * @see #getStudent()
     * @generated
     */
    EReference getStudent_TestResults();

    /**
     * Returns the meta object for class '
     * {@link net.juckel.rcp.databinding.aggregate.tests.model.ExamResult
     * <em>Exam Result</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for class '<em>Exam Result</em>'.
     * @see net.juckel.rcp.databinding.aggregate.tests.model.ExamResult
     * @generated
     */
    EClass getExamResult();

    /**
     * Returns the meta object for the attribute '
     * {@link net.juckel.rcp.databinding.aggregate.tests.model.ExamResult#getScore
     * <em>Score</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for the attribute '<em>Score</em>'.
     * @see net.juckel.rcp.databinding.aggregate.tests.model.ExamResult#getScore()
     * @see #getExamResult()
     * @generated
     */
    EAttribute getExamResult_Score();

    /**
     * Returns the meta object for the reference '
     * {@link net.juckel.rcp.databinding.aggregate.tests.model.ExamResult#getStudent
     * <em>Student</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for the reference '<em>Student</em>'.
     * @see net.juckel.rcp.databinding.aggregate.tests.model.ExamResult#getStudent()
     * @see #getExamResult()
     * @generated
     */
    EReference getExamResult_Student();

    /**
     * Returns the meta object for the attribute '
     * {@link net.juckel.rcp.databinding.aggregate.tests.model.ExamResult#getWeight
     * <em>Weight</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for the attribute '<em>Weight</em>'.
     * @see net.juckel.rcp.databinding.aggregate.tests.model.ExamResult#getWeight()
     * @see #getExamResult()
     * @generated
     */
    EAttribute getExamResult_Weight();

    /**
     * Returns the factory that creates the instances of the model. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the factory that creates the instances of the model.
     * @generated
     */
    ClassroomFactory getClassroomFactory();

    /**
     * <!-- begin-user-doc --> Defines literals for the meta objects that
     * represent
     * <ul>
     * <li>each class,</li>
     * <li>each feature of each class,</li>
     * <li>each enum,</li>
     * <li>and each data type</li>
     * </ul>
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    interface Literals {
        /**
         * The meta object literal for the '
         * {@link net.juckel.rcp.databinding.aggregate.tests.model.impl.StudentImpl
         * <em>Student</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
         * -->
         * 
         * @see net.juckel.rcp.databinding.aggregate.tests.model.impl.StudentImpl
         * @see net.juckel.rcp.databinding.aggregate.tests.model.impl.ClassroomPackageImpl#getStudent()
         * @generated
         */
        EClass STUDENT = eINSTANCE.getStudent();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute
         * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EAttribute STUDENT__NAME = eINSTANCE.getStudent_Name();

        /**
         * The meta object literal for the '<em><b>Test Results</b></em>'
         * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference STUDENT__TEST_RESULTS = eINSTANCE.getStudent_TestResults();

        /**
         * The meta object literal for the '
         * {@link net.juckel.rcp.databinding.aggregate.tests.model.impl.ExamResultImpl
         * <em>Exam Result</em>}' class. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         * 
         * @see net.juckel.rcp.databinding.aggregate.tests.model.impl.ExamResultImpl
         * @see net.juckel.rcp.databinding.aggregate.tests.model.impl.ClassroomPackageImpl#getExamResult()
         * @generated
         */
        EClass EXAM_RESULT = eINSTANCE.getExamResult();

        /**
         * The meta object literal for the '<em><b>Score</b></em>' attribute
         * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EAttribute EXAM_RESULT__SCORE = eINSTANCE.getExamResult_Score();

        /**
         * The meta object literal for the '<em><b>Student</b></em>' reference
         * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference EXAM_RESULT__STUDENT = eINSTANCE.getExamResult_Student();

        /**
         * The meta object literal for the '<em><b>Weight</b></em>' attribute
         * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EAttribute EXAM_RESULT__WEIGHT = eINSTANCE.getExamResult_Weight();

    }

} // ClassroomPackage
