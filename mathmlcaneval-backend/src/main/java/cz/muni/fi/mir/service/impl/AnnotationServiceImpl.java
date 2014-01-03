/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.service.impl;

import cz.muni.fi.mir.dao.AnnotationDAO;
import cz.muni.fi.mir.domain.Annotation;
import cz.muni.fi.mir.domain.AnnotationFlag;
import cz.muni.fi.mir.domain.User;
import cz.muni.fi.mir.service.AnnotationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Empt
 */
@Service(value = "annotationService")
public class AnnotationServiceImpl implements AnnotationService
{
    @Autowired private AnnotationDAO annotationDAO;    
    
    @Override
    @Transactional(readOnly = false)
    public void createAnnotation(Annotation annotation)
    {
        annotationDAO.createAnnotation(annotation);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateAnnotation(Annotation annotation)
    {
        annotationDAO.updateAnnotation(annotation);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteAnnotation(Annotation annotation)
    {
        annotationDAO.deleteAnnotation(annotation);
    }

    @Override
    @Transactional(readOnly = true)
    public Annotation getAnnotationByID(Long id)
    {
        return annotationDAO.getAnnotationByID(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Annotation> getAllAnnotations()
    {
        return annotationDAO.getAllAnnotations();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Annotation> getAnnotationByUser(User user)
    {
        return annotationDAO.getAnnotationByUser(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Annotation> getAnnotationByFlag(AnnotationFlag flag)
    {
        return annotationDAO.getAnnotationByFlag(flag);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Annotation> findByNote(String note)
    {
        return annotationDAO.findByNote(note);
    }    
}
