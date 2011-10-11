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
package net.juckel.rcp.databinding.aggregate.tests.model.impl;

import net.juckel.rcp.databinding.aggregate.tests.model.ClassroomFactory;
import net.juckel.rcp.databinding.aggregate.tests.model.ClassroomPackage;
import net.juckel.rcp.databinding.aggregate.tests.model.ExamResult;
import net.juckel.rcp.databinding.aggregate.tests.model.Student;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!--
 * end-user-doc -->
 * 
 * @generated
 */
public class ClassroomFactoryImpl extends EFactoryImpl implements
        ClassroomFactory {
    /**
     * Creates the default factory implementation. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     */
    public static ClassroomFactory init() {
        try {
            ClassroomFactory theClassroomFactory = (ClassroomFactory) EPackage.Registry.INSTANCE
                    .getEFactory("http://net.juckel/rcp/databinding/tests/");
            if (theClassroomFactory != null) {
                return theClassroomFactory;
            }
        } catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new ClassroomFactoryImpl();
    }

    /**
     * Creates an instance of the factory. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     */
    public ClassroomFactoryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EObject create(EClass eClass) {
        switch (eClass.getClassifierID()) {
        case ClassroomPackage.STUDENT:
            return createStudent();
        case ClassroomPackage.EXAM_RESULT:
            return createExamResult();
        default:
            throw new IllegalArgumentException("The class '" + eClass.getName()
                    + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public Student createStudent() {
        StudentImpl student = new StudentImpl();
        return student;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public ExamResult createExamResult() {
        ExamResultImpl examResult = new ExamResultImpl();
        return examResult;
    }

    /**
     * 
     * @param score
     * @param weight
     * @return
     * @generated NOT
     */
    public ExamResult createExamResult(double score, double weight) {
        ExamResult er = createExamResult();
        er.setScore(score);
        er.setWeight(weight);
        return er;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public ClassroomPackage getClassroomPackage() {
        return (ClassroomPackage) getEPackage();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @deprecated
     * @generated
     */
    @Deprecated
    public static ClassroomPackage getPackage() {
        return ClassroomPackage.eINSTANCE;
    }

} // ClassroomFactoryImpl
