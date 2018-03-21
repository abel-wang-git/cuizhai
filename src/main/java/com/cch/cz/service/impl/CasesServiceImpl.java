package com.cch.cz.service.impl;

import com.cch.cz.authority.entity.User;
import com.cch.cz.authority.mapper.UserMapper;
import com.cch.cz.base.service.impl.BaseServiceImpl;
import com.cch.cz.entity.Cases;
import com.cch.cz.entity.Staff;
import com.cch.cz.mapper.StaffMapper;
import com.cch.cz.service.CasesService;
import com.cch.cz.service.StaffService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

/**
 * Created by Administrator on 2018/3/18.
 *
 */
@Service
public class CasesServiceImpl extends BaseServiceImpl<Cases,Long> implements CasesService {

}
