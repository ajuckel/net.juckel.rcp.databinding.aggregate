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

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Student</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.juckel.rcp.databinding.aggregate.tests.model.Student#getName <em>Name</em>}</li>
 *   <li>{@link net.juckel.rcp.databinding.aggregate.tests.model.Student#getTestResults <em>Test Results</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.juckel.rcp.databinding.aggregate.tests.model.ClassroomPackage#getStudent()
 * @model
 * @generated
 */
public interface Student extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see net.juckel.rcp.databinding.aggregate.tests.model.ClassroomPackage#getStudent_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link net.juckel.rcp.databinding.aggregate.tests.model.Student#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Test Results</b></em>' reference list.
	 * The list contents are of type {@link net.juckel.rcp.databinding.aggregate.tests.model.ExamResult}.
	 * It is bidirectional and its opposite is '{@link net.juckel.rcp.databinding.aggregate.tests.model.ExamResult#getStudent <em>Student</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Test Results</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Test Results</em>' reference list.
	 * @see net.juckel.rcp.databinding.aggregate.tests.model.ClassroomPackage#getStudent_TestResults()
	 * @see net.juckel.rcp.databinding.aggregate.tests.model.ExamResult#getStudent
	 * @model opposite="student"
	 * @generated
	 */
	EList<ExamResult> getTestResults();

} // Student
