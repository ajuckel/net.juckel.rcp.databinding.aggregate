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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Test Result</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.juckel.rcp.databinding.aggregate.tests.model.ExamResult#getScore <em>Score</em>}</li>
 *   <li>{@link net.juckel.rcp.databinding.aggregate.tests.model.ExamResult#getStudent <em>Student</em>}</li>
 *   <li>{@link net.juckel.rcp.databinding.aggregate.tests.model.ExamResult#getWeight <em>Weight</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.juckel.rcp.databinding.aggregate.tests.model.ClassroomPackage#getExamResult()
 * @model
 * @generated
 */
public interface ExamResult extends EObject, Comparable<ExamResult> {
	/**
	 * Returns the value of the '<em><b>Score</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Score</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Score</em>' attribute.
	 * @see #setScore(double)
	 * @see net.juckel.rcp.databinding.aggregate.tests.model.ClassroomPackage#getExamResult_Score()
	 * @model
	 * @generated
	 */
	double getScore();

	/**
	 * Sets the value of the '{@link net.juckel.rcp.databinding.aggregate.tests.model.ExamResult#getScore <em>Score</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Score</em>' attribute.
	 * @see #getScore()
	 * @generated
	 */
	void setScore(double value);

	/**
	 * Returns the value of the '<em><b>Student</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link net.juckel.rcp.databinding.aggregate.tests.model.Student#getTestResults <em>Test Results</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Student</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Student</em>' reference.
	 * @see #setStudent(Student)
	 * @see net.juckel.rcp.databinding.aggregate.tests.model.ClassroomPackage#getExamResult_Student()
	 * @see net.juckel.rcp.databinding.aggregate.tests.model.Student#getTestResults
	 * @model opposite="testResults"
	 * @generated
	 */
	Student getStudent();

	/**
	 * Sets the value of the '{@link net.juckel.rcp.databinding.aggregate.tests.model.ExamResult#getStudent <em>Student</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Student</em>' reference.
	 * @see #getStudent()
	 * @generated
	 */
	void setStudent(Student value);

	/**
	 * Returns the value of the '<em><b>Weight</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Weight</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Weight</em>' attribute.
	 * @see #setWeight(double)
	 * @see net.juckel.rcp.databinding.aggregate.tests.model.ClassroomPackage#getExamResult_Weight()
	 * @model
	 * @generated
	 */
	double getWeight();

	/**
	 * Sets the value of the '{@link net.juckel.rcp.databinding.aggregate.tests.model.ExamResult#getWeight <em>Weight</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Weight</em>' attribute.
	 * @see #getWeight()
	 * @generated
	 */
	void setWeight(double value);

} // ExamResult
