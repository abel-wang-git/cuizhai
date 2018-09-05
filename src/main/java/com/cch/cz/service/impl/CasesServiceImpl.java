package com.cch.cz.service.impl;

import com.cch.cz.authority.entity.Role;
import com.cch.cz.authority.mapper.RoleMapper;
import com.cch.cz.base.service.impl.BaseServiceImpl;
import com.cch.cz.common.UtilFun;
import com.cch.cz.entity.*;
import com.cch.cz.entity.enu.MessageType;
import com.cch.cz.entity.enu.MgStatus;
import com.cch.cz.exception.ObjectNullException;
import com.cch.cz.mapper.*;
import com.cch.cz.service.CasesService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by Administrator on 2018/3/18.
 *
 */
@Service
public class CasesServiceImpl extends BaseServiceImpl<Cases, Long> implements CasesService {
    @Resource
    private CasesMapper casesMapper;
    @Resource
    private AdjustLogMapper adjustLogMapper;
    @Resource
    private StaffMapper staffMapper;
    @Resource
    private MessageMapper messageMapper;
    @Resource
    private MessageStatusMapper messageStatusMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private ExtendMapper extendMapper;

    @Override
    @Transactional
    public void expCase(List<Cases> casesList) throws IllegalAccessException {
        HashSet<String> idc = new HashSet<>();
        HashSet<String> com = new HashSet<>();
        for (Cases c : casesList) {
            if (c.getStaffId() == null) {
                idc.add(c.getIdCard());
                com.add(c.getContractNum());
            }

        }
        String idcs = StringUtils.join(idc.toArray(), "','");
        String coms = StringUtils.join(com.toArray(), "','");

        List<Cases> list = casesMapper.findinIdcard("'" + idcs + "'");
        List<Cases> comes = casesMapper.findinIdcard("'" + coms + "'");
        list.addAll(comes);
        for (Cases c : list) {
            for (Cases c1 : casesList) {
                if (c.getIdCard().equals(c1.getIdCard()) || c.getContractNum().equals(c1.getContractsNum())) {
                    c1.setStaffId(c.getStaffId());
                    c1.setCompanyId(c.getCompanyId());
                }
            }
        }


        for (Cases c : casesList) {
            casesMapper.save(c);
            for (Extend e:c.getExtend()) {
                if (UtilFun.isEmptyString(e.getPhone())){
                    e.setIdCard(c.getIdCard());
                    extendMapper.save(e);
                }
            }
//            Supplement supplement = new Supplement();
//            supplement.setContractNum(c.getContractNum());
//            supplement.setCustomerOtherPhone(c.getCustomerOtherPhone());
//            supplement.setCustomerRelaOther(c.getCustomerRelaOther());
//            supplement.setCustomerRelativeOther(c.getCustomerRelativeOther());
//            supplement.setCustomerRelationship(c.getCustomerRelationship());
//            supplement.setCustomerRelativeName(c.getCustomerRelativeName());
//            supplement.setCustomerRelativePhone(c.getCustomerRelativePhone());
//            supplement.setCustomerSpouse(c.getCustomerSpouse());
//            supplement.setCustomerSpousePhone(c.getCustomerSpousePhone());
//            Field[] fields = Supplement.class.getDeclaredFields();
//            for (Field f : fields) {
//                f.setAccessible(true);
//                if (f.get(supplement) != null) {
//                    supplementMapper.save(supplement);
//                    break;
//                }
//                f.setAccessible(false);
//            }
        }

    }

    @Override
    @Transactional
    public void randomToStaff(String[] staff, List<Cases> cases) {
        HashSet<String> idc = new HashSet<>();
        HashSet<String> com = new HashSet<>();
        for (Cases c : cases) {
            idc.add(c.getIdCard());
            com.add(c.getContractNum());
        }
        String idcs = StringUtils.join(idc.toArray(), "','");
        String coms = StringUtils.join(com.toArray(), "','");
        List<Cases> list = casesMapper.findinIdcardno("'" + idcs + "'");
        List<Cases> comes = casesMapper.findinIdcardno("'" + coms + "'");
        list.addAll(comes);
        int index = 0;
        Cases where = new Cases();
        where.setStatus(Cases.NORMAL);
        int length = cases.size();
        for (int i = 0; i < length; i++) {
            Cases c = cases.get(i);
            if (index == staff.length) index = 0;
            c.setStaffId(staff[index]);

            update(c);
            index++;
        }
        //寻找相同合同号和身份证号未分配的分配给同一个员工
        for (Cases c : list) {
            for (Cases c1 : cases) {
                if (c1.getIdCard().equals(c.getIdCard()) || c1.getContractNum().equals(c.getContractsNum())) {
                    c.setCompanyId(c1.getCompanyId());
                    c.setStaffId(c1.getStaffId());
                    update(c);
                }
            }
        }
    }

    @Override
    public List<Map> groupCasesByArea() {
        return casesMapper.groupCasesByArea();
    }

    @Override
    @Transactional
    public void allotCaseToCompany(List<String> areas, String company) {
        for (String area : areas) {
            casesMapper.allotCompany(company, "%" + area + "%");
        }
    }

    @Override
    @Transactional
    public void allotStaff(List<Cases> cases, Staff staff) {
        for (Cases c : cases) {
            casesMapper.allotStaff(c.getId(), staff.getLoginName());
        }
    }

    @Override
    public List<Cases> listByCompanyNoStaff(Cases cases) {
        return casesMapper.listByCompanyNoStaff(cases);
    }

    @Override
    public List<Cases> listByStaff(Cases cases) {
        return casesMapper.listByStaff(cases);
    }


    @Override
    public List<Map> listByCompany(Long company, String staff, int status) {
        List<Map> list = casesMapper.listByCompany(company, staff, status);
        Map map = new HashMap();
        map.put("money", 0);
        map.put("num", 0);
        if (!UtilFun.isEmptyList(list) || list.size() <= 0) list.add(map);
        return list;
    }

    @Override
    @Transactional
    public void managerCase(List<Cases> cases) throws ObjectNullException {
        Role admin = roleMapper.getByName("admin");
        if (admin == null) {
            admin = roleMapper.getByName("branchManager");
        }
        Message message = new Message();
        MessageStatus messageStatus = new MessageStatus();
        for (int i = 0; i < cases.size(); i++) {
            Staff staff = new Staff();
            staff.setCompanyId(cases.get(i).getCompanyId());
            staff.setPlace(Long.toString(admin.getId()));
            List<Staff> staff1 = staffMapper.findByEntity(staff);
            if (staff1 == null || staff1.size() == 0) throw new ObjectNullException("该公司不存在管理员");

            if (cases.get(i).getStatus() == Cases.REVOKE) cases.get(i).setRevokeDate(UtilFun.DateToString(new Date(), UtilFun.YYYYMMDD));
            //结案
            if (cases.get(i).getStatus() == Cases.END) {

                cases.get(i).setEndDate(UtilFun.DateToString(new Date(), UtilFun.YYYYMMDD));
                message.setId(UUID.randomUUID().getLeastSignificantBits());
                message.setCaseId(cases.get(i).getId());
                message.setMessage("结案申请：" + cases.get(i).getEndReason());
                message.setType(MessageType.END.value());
                message.setSender(cases.get(i).getStaffId());
                message.setDate(UtilFun.DateToString(new Date(), UtilFun.YYYYMMDD));
                messageMapper.save(message);
                messageStatus.setMessageId(message.getId());
                messageStatus.setReceiver(staff1.get(0).getLoginName());
                messageStatus.setStatus(MgStatus.NOREAD.value());
                messageStatusMapper.save(messageStatus);

            }
            //留案
            if (cases.get(i).getStatus() == Cases.RETAIN) {
                cases.get(i).setRethinDate(UtilFun.DateToString(new Date(), UtilFun.YYYYMMDD));
                message.setId(UUID.randomUUID().getMostSignificantBits());
                message.setCaseId(cases.get(i).getId());
                message.setMessage("留案申请：" + cases.get(i).getRetainReason());
                message.setType(MessageType.RETAIN.value());
                message.setSender(cases.get(i).getStaffId());
                message.setDate(UtilFun.DateToString(new Date(), UtilFun.YYYYMMDD));
                messageMapper.save(message);
                messageStatus.setMessageId(message.getId());
                messageStatus.setReceiver(staff1.get(0).getLoginName());
                messageStatus.setStatus(MgStatus.NOREAD.value());
                messageStatusMapper.save(messageStatus);
            }
            this.update(cases.get(i));
        }

    }

    @Override
    public List<Cases> dynamicList(Cases cases) {
        return casesMapper.dynamicList(cases);
    }

    @Override
    @Transactional
    public void randomAllot(String[] company,List<Map> cases) {
        for (int i = 0; i < cases.size(); i++) {
            for (int j = 0; j <company.length ; j++) {
                casesMapper.randomAllot(company[j],
                        cases.get(i).get("caseName").toString(),
                        Integer.parseInt(cases.get(i).get("count").toString())/company.length+1);
            }
        }
    }

    @Override
    public List<Map> groupByCaseName() {
        return casesMapper.groupCasesByCaseName();
    }



    private int identical(String[] staff, List<Cases> cases, int index, List<Cases> identical, int length, int i) {
        if (UtilFun.isEmptyList(identical)) {
            for (Cases ac : identical) {
                if (ac.getStaffId() == null) {
                    ac.setStaffId(staff[index]);
                    update(ac);
                    //分配过得直接从list里删除不作二次分配
                    for (int j = i + 1; j < length; j++) {
                        if (ac.getId().longValue() == cases.get(j).getId().longValue()) {
                            cases.remove(i);
                            length = cases.size();
                        }
                    }
                }
            }
        }
        return length;
    }


    @Override
    @Transactional
    public void adjust(List<Cases> ids, String staffid) {
        AdjustLog adjustLog = new AdjustLog();
        adjustLog.setNewStaff(staffid);
        adjustLog.setDate(new Date());
        for (Cases cases :ids) {
            adjustLog.setCaseId(cases.getId());
            adjustLog.setOldStaff(cases.getStaffId());
            adjustLogMapper.save(adjustLog);
            cases.setStaffId(staffid);
            update(cases);

        }
    }

    @Override
    public List<Map> listByAdjust() {
        List<Map> list=casesMapper.listByAdjust();
        List<Map> staffs= new ArrayList<>();
        for(Map map : list){
            String oldstaff = (String)map.get("oldstaff");
            String newstaff = (String)map.get("newstaff");
            Staff staff =staffMapper.findOne(new Staff(),oldstaff);
            Staff staff1 =staffMapper.findOne(new Staff(),newstaff);
            if(staff!=null) map.put("oldstaff",staff.getName());
            if(staff!=null) map.put("newstaff",staff1.getName());
            staffs.add(map);
        }
        return staffs;
    }

    @Override
    @Transactional
    public void allotToCompany(List<Cases> cases, String company) {
        for (Cases c :cases) {
            c.setCompanyId(Long.parseLong(company));
            update(c);
        }
        //list是根据身份证和合同号排序的只需要找和最后一条相同的未分配的case
        Cases lastCase = cases.get(cases.size() - 1);
        Cases where = new Cases();
        where.setStatus(Cases.NORMAL);
        //查找身份证相同的
        where.setIdCard(lastCase.getIdCard());
        List<Cases> idcard = casesMapper.findByEntity(where);
        identical(company, idcard);

        //合同号相同的
        where.setIdCard(null);
        where.setContractNum(lastCase.getContractNum());
        List<Cases> contractNum = casesMapper.findByEntity(where);
        identical(company, contractNum);
    }

    private void identical(String company, List<Cases> idcard) {
        if (idcard.size() > 0) {
            for (Cases c : idcard) {
                if (c.getStaffId() == null && c.getCompanyId() == -1) {
                    c.setCompanyId(Long.parseLong(company));
                    update(c);
                }
            }
        }
    }


    @Override
    public void autoRevoke() {
        //查到到案的案件
        String now = UtilFun.DateToString(new Date(),"yyyy/MM/dd");
        casesMapper.autoRevoke(now);
    }

    @Override
    public Staff findStaffByIdcard(String idcard) {
        String staff_id = casesMapper.findStaffByIdcard(idcard);
        return staffMapper.findOne(new Staff(), staff_id);
    }

    @Override
    public List<Map> isUrge(String isUrge, Long company, String staff) {

        return casesMapper.isUrge(isUrge, company, staff);
    }

    @Override
    public List<Cases> todayUrge(String s) {
        return casesMapper.todayUrge(s);
    }


}
