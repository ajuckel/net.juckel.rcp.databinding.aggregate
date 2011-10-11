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

import net.juckel.rcp.databinding.aggregate.tests.model.ClassroomPackage;
import net.juckel.rcp.databinding.aggregate.tests.model.Student;
import net.juckel.rcp.databinding.aggregate.tests.model.ExamResult;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Test Result</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>
 * {@link net.juckel.rcp.databinding.aggregate.tests.model.impl.ExamResultImpl#getScore
 * <em>Score</em>}</li>
 * <li>
 * {@link net.juckel.rcp.databinding.aggregate.tests.model.impl.ExamResultImpl#getStudent
 * <em>Student</em>}</li>
 * <li>
 * {@link net.juckel.rcp.databinding.aggregate.tests.model.impl.ExamResultImpl#getWeight
 * <em>Weight</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class ExamResultImpl extends EObjectImpl implements ExamResult {
    /**
     * The default value of the '{@link #getScore() <em>Score</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getScore()
     * @generated
     * @ordered
     */
    protected static final double SCORE_EDEFAULT = 0.0;

    /**
     * The cached value of the '{@link #getScore() <em>Score</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getScore()
     * @generated
     * @ordered
     */
    protected double score = SCORE_EDEFAULT;

    /**
     * The cached value of the '{@link #getStudent() <em>Student</em>}'
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getStudent()
     * @generated
     * @ordered
     */
    protected Student student;

    /**
     * The default value of the '{@link #getWeight() <em>Weight</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getWeight()
     * @generated
     * @ordered
     */
    protected static final double WEIGHT_EDEFAULT = 0.0;

    /**
     * The cached value of the '{@link #getWeight() <em>Weight</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getWeight()
     * @generated
     * @ordered
     */
    protected double weight = WEIGHT_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    protected ExamResultImpl() {
        super();
        setWeight(1.0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ClassroomPackage.Literals.EXAM_RESULT;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public double getScore() {
        return score;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setScore(double newScore) {
        double oldScore = score;
        score = newScore;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    ClassroomPackage.EXAM_RESULT__SCORE, oldScore, score));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public Student getStudent() {
        if (student != null && student.eIsProxy()) {
            InternalEObject oldStudent = (InternalEObject) student;
            student = (Student) eResolveProxy(oldStudent);
            if (student != oldStudent) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE,
                            ClassroomPackage.EXAM_RESULT__STUDENT, oldStudent,
                            student));
            }
        }
        return student;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public Student basicGetStudent() {
        return student;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public NotificationChain basicSetStudent(Student newStudent,
            NotificationChain msgs) {
        Student oldStudent = student;
        student = newStudent;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this,
                    Notification.SET, ClassroomPackage.EXAM_RESULT__STUDENT,
                    oldStudent, newStudent);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setStudent(Student newStudent) {
        if (newStudent != student) {
            NotificationChain msgs = null;
            if (student != null)
                msgs = ((InternalEObject) student).eInverseRemove(this,
                        ClassroomPackage.STUDENT__TEST_RESULTS, Student.class,
                        msgs);
            if (newStudent != null)
                msgs = ((InternalEObject) newStudent).eInverseAdd(this,
                        ClassroomPackage.STUDENT__TEST_RESULTS, Student.class,
                        msgs);
            msgs = basicSetStudent(newStudent, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    ClassroomPackage.EXAM_RESULT__STUDENT, newStudent,
                    newStudent));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public double getWeight() {
        return weight;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setWeight(double newWeight) {
        double oldWeight = weight;
        weight = newWeight;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    ClassroomPackage.EXAM_RESULT__WEIGHT, oldWeight, weight));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public NotificationChain eInverseAdd(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case ClassroomPackage.EXAM_RESULT__STUDENT:
            if (student != null)
                msgs = ((InternalEObject) student).eInverseRemove(this,
                        ClassroomPackage.STUDENT__TEST_RESULTS, Student.class,
                        msgs);
            return basicSetStudent((Student) otherEnd, msgs);
        }
        return super.eInverseAdd(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case ClassroomPackage.EXAM_RESULT__STUDENT:
            return basicSetStudent(null, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case ClassroomPackage.EXAM_RESULT__SCORE:
            return getScore();
        case ClassroomPackage.EXAM_RESULT__STUDENT:
            if (resolve)
                return getStudent();
            return basicGetStudent();
        case ClassroomPackage.EXAM_RESULT__WEIGHT:
            return getWeight();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
        case ClassroomPackage.EXAM_RESULT__SCORE:
            setScore((Double) newValue);
            return;
        case ClassroomPackage.EXAM_RESULT__STUDENT:
            setStudent((Student) newValue);
            return;
        case ClassroomPackage.EXAM_RESULT__WEIGHT:
            setWeight((Double) newValue);
            return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
        case ClassroomPackage.EXAM_RESULT__SCORE:
            setScore(SCORE_EDEFAULT);
            return;
        case ClassroomPackage.EXAM_RESULT__STUDENT:
            setStudent((Student) null);
            return;
        case ClassroomPackage.EXAM_RESULT__WEIGHT:
            setWeight(WEIGHT_EDEFAULT);
            return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
        case ClassroomPackage.EXAM_RESULT__SCORE:
            return score != SCORE_EDEFAULT;
        case ClassroomPackage.EXAM_RESULT__STUDENT:
            return student != null;
        case ClassroomPackage.EXAM_RESULT__WEIGHT:
            return weight != WEIGHT_EDEFAULT;
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy())
            return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (score: ");
        result.append(score);
        result.append(", weight: ");
        result.append(weight);
        result.append(')');
        return result.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(score);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((student == null) ? 0 : student.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ExamResultImpl other = (ExamResultImpl) obj;
        if (Double.doubleToLongBits(score) != Double
                .doubleToLongBits(other.score))
            return false;
        if (Double.doubleToLongBits(weight) != Double
                .doubleToLongBits(other.weight))
            return false;
        if (student == null) {
            if (other.student != null)
                return false;
        } else if (!student.equals(other.student))
            return false;
        return true;
    }

    public int compareTo(ExamResult o) {
        if (o == null) {
            return 1;
        }
        double thisTotal = this.getScore() * this.getWeight();
        double otherTotal = o.getScore() * o.getWeight();

        return Double.compare(thisTotal, otherTotal);
    }

} // ExamResultImpl
