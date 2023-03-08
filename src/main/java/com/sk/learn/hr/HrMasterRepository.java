package com.sk.learn.hr;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HrMasterRepository extends JpaRepository<HrMasterEntity, String>
{
}