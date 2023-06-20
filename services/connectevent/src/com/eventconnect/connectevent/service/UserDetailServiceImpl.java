/*Copyright (c) 2023-2024 wavemaker.com All Rights Reserved.
 This software is the confidential and proprietary information of wavemaker.com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with wavemaker.com*/
package com.eventconnect.connectevent.service;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.wavemaker.commons.InvalidInputException;
import com.wavemaker.commons.MessageResource;
import com.wavemaker.runtime.commons.file.model.Downloadable;
import com.wavemaker.runtime.data.annotations.EntityService;
import com.wavemaker.runtime.data.dao.WMGenericDao;
import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.export.DataExportOptions;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.data.model.AggregationInfo;

import com.eventconnect.connectevent.Attendee;
import com.eventconnect.connectevent.EventDetail;
import com.eventconnect.connectevent.UserDetail;


/**
 * ServiceImpl object for domain model class UserDetail.
 *
 * @see UserDetail
 */
@Service("connectevent.UserDetailService")
@Validated
@EntityService(entityClass = UserDetail.class, serviceId = "connectevent")
public class UserDetailServiceImpl implements UserDetailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailServiceImpl.class);

    @Lazy
    @Autowired
    @Qualifier("connectevent.EventDetailService")
    private EventDetailService eventDetailService;

    @Lazy
    @Autowired
    @Qualifier("connectevent.AttendeeService")
    private AttendeeService attendeeService;

    @Autowired
    @Qualifier("connectevent.UserDetailDao")
    private WMGenericDao<UserDetail, Integer> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<UserDetail, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "connecteventTransactionManager")
    @Override
    public UserDetail create(UserDetail userDetail) {
        LOGGER.debug("Creating a new UserDetail with information: {}", userDetail);

        UserDetail userDetailCreated = this.wmGenericDao.create(userDetail);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(userDetailCreated);
    }

    @Transactional(readOnly = true, value = "connecteventTransactionManager")
    @Override
    public UserDetail getById(Integer userdetailId) {
        LOGGER.debug("Finding UserDetail by id: {}", userdetailId);
        return this.wmGenericDao.findById(userdetailId);
    }

    @Transactional(readOnly = true, value = "connecteventTransactionManager")
    @Override
    public UserDetail findById(Integer userdetailId) {
        LOGGER.debug("Finding UserDetail by id: {}", userdetailId);
        try {
            return this.wmGenericDao.findById(userdetailId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No UserDetail found with id: {}", userdetailId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "connecteventTransactionManager")
    @Override
    public List<UserDetail> findByMultipleIds(List<Integer> userdetailIds, boolean orderedReturn) {
        LOGGER.debug("Finding UserDetails by ids: {}", userdetailIds);

        return this.wmGenericDao.findByMultipleIds(userdetailIds, orderedReturn);
    }

    @Transactional(readOnly = true, value = "connecteventTransactionManager")
    @Override
    public UserDetail getByEmailId(String emailId) {
        Map<String, Object> emailIdMap = new HashMap<>();
        emailIdMap.put("emailId", emailId);

        LOGGER.debug("Finding UserDetail by unique keys: {}", emailIdMap);
        return this.wmGenericDao.findByUniqueKey(emailIdMap);
    }

    @Transactional(readOnly = true, value = "connecteventTransactionManager")
    @Override
    public UserDetail getByPhoneNumber(String phoneNumber) {
        Map<String, Object> phoneNumberMap = new HashMap<>();
        phoneNumberMap.put("phoneNumber", phoneNumber);

        LOGGER.debug("Finding UserDetail by unique keys: {}", phoneNumberMap);
        return this.wmGenericDao.findByUniqueKey(phoneNumberMap);
    }

    @Transactional(readOnly = true, value = "connecteventTransactionManager")
    @Override
    public UserDetail getByPassword(String password) {
        Map<String, Object> passwordMap = new HashMap<>();
        passwordMap.put("password", password);

        LOGGER.debug("Finding UserDetail by unique keys: {}", passwordMap);
        return this.wmGenericDao.findByUniqueKey(passwordMap);
    }

    @Transactional(rollbackFor = EntityNotFoundException.class, value = "connecteventTransactionManager")
    @Override
    public UserDetail update(UserDetail userDetail) {
        LOGGER.debug("Updating UserDetail with information: {}", userDetail);

        this.wmGenericDao.update(userDetail);
        this.wmGenericDao.refresh(userDetail);

        return userDetail;
    }

    @Transactional(value = "connecteventTransactionManager")
    @Override
    public UserDetail partialUpdate(Integer userdetailId, Map<String, Object>userDetailPatch) {
        LOGGER.debug("Partially Updating the UserDetail with id: {}", userdetailId);

        UserDetail userDetail = getById(userdetailId);

        try {
            ObjectReader userDetailReader = this.objectMapper.reader().forType(UserDetail.class).withValueToUpdate(userDetail);
            userDetail = userDetailReader.readValue(this.objectMapper.writeValueAsString(userDetailPatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", userDetailPatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        userDetail = update(userDetail);

        return userDetail;
    }

    @Transactional(value = "connecteventTransactionManager")
    @Override
    public UserDetail delete(Integer userdetailId) {
        LOGGER.debug("Deleting UserDetail with id: {}", userdetailId);
        UserDetail deleted = this.wmGenericDao.findById(userdetailId);
        if (deleted == null) {
            LOGGER.debug("No UserDetail found with id: {}", userdetailId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), UserDetail.class.getSimpleName(), userdetailId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "connecteventTransactionManager")
    @Override
    public void delete(UserDetail userDetail) {
        LOGGER.debug("Deleting UserDetail with {}", userDetail);
        this.wmGenericDao.delete(userDetail);
    }

    @Transactional(readOnly = true, value = "connecteventTransactionManager")
    @Override
    public Page<UserDetail> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all UserDetails");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "connecteventTransactionManager")
    @Override
    public Page<UserDetail> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all UserDetails");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "connecteventTransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service connectevent for table UserDetail to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "connecteventTransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service connectevent for table UserDetail to {} format", options.getExportType());
        this.wmGenericDao.export(options, pageable, outputStream);
    }

    @Transactional(readOnly = true, value = "connecteventTransactionManager")
    @Override
    public long count(String query) {
        return this.wmGenericDao.count(query);
    }

    @Transactional(readOnly = true, value = "connecteventTransactionManager")
    @Override
    public Page<Map<String, Object>> getAggregatedValues(AggregationInfo aggregationInfo, Pageable pageable) {
        return this.wmGenericDao.getAggregatedValues(aggregationInfo, pageable);
    }

    @Transactional(readOnly = true, value = "connecteventTransactionManager")
    @Override
    public Page<Attendee> findAssociatedAttendees(Integer id, Pageable pageable) {
        LOGGER.debug("Fetching all associated attendees");

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("userDetail.id = '" + id + "'");

        return attendeeService.findAll(queryBuilder.toString(), pageable);
    }

    @Transactional(readOnly = true, value = "connecteventTransactionManager")
    @Override
    public Page<EventDetail> findAssociatedEventDetails(Integer id, Pageable pageable) {
        LOGGER.debug("Fetching all associated eventDetails");

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("userDetail.id = '" + id + "'");

        return eventDetailService.findAll(queryBuilder.toString(), pageable);
    }

    /**
     * This setter method should only be used by unit tests
     *
     * @param service EventDetailService instance
     */
    protected void setEventDetailService(EventDetailService service) {
        this.eventDetailService = service;
    }

    /**
     * This setter method should only be used by unit tests
     *
     * @param service AttendeeService instance
     */
    protected void setAttendeeService(AttendeeService service) {
        this.attendeeService = service;
    }

}