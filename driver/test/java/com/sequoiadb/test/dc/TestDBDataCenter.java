package com.sequoiadb.test.dc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bson.BSONObject;
import org.bson.BasicBSONObject;
import org.bson.types.BasicBSONList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sequoiadb.base.CollectionSpace;
import com.sequoiadb.base.DBCollection;
import com.sequoiadb.base.DBCursor;
import com.sequoiadb.base.DBDataCenter;
import com.sequoiadb.base.DBQuery;
import com.sequoiadb.base.Sequoiadb;
import com.sequoiadb.exception.BaseException;
import com.sequoiadb.testdata.SDBTestHelper;
import com.sequoiadb.test.common.*;
public class TestDBDataCenter {
    private static Sequoiadb sdb;
    private static DBDataCenter dc;
    private static boolean isCluster = false;

    @BeforeClass
    public static void setConnBeforeClass() throws Exception {

    }

    @AfterClass
    public static void DropConnAfterClass() throws Exception {

    }

    @Before
    public void setUp() throws Exception {
        sdb = new Sequoiadb(Constants.COOR_NODE_CONN, "", "");
        isCluster = Constants.isCluster();
        if(!isCluster){
            return;
        }
        dc  = sdb.getDataCenter();
    }

    @After
    public void tearDown() throws Exception {
        if(!isCluster){
            return;
        }
        dc.activate();
        sdb.disconnect();
    }

    @Test
    public void testGetDetail() {
        if(!isCluster){
            return;
        }
        String name = dc.getName();
        BSONObject detail = dc.getDetail();
        SDBTestHelper.println("name=" + name);
        SDBTestHelper.println(detail.toString());
    }
    
    @Test
    public void testActivate() {
        if(!isCluster){
            return;
        }
        BSONObject detail = dc.getDetail();
        Boolean isActive  = (Boolean)detail.get("Activated");
        assertEquals(isActive, Boolean.TRUE);
        
        dc.deactivate();
        
        detail   = dc.getDetail();
        isActive = (Boolean)detail.get("Activated");
        assertEquals(isActive, Boolean.FALSE);
    }
    
    @Test
    public void testImage() {
        if(!isCluster){
            return;
        }
    }
    
    @Test
    public void testReadonly() {
        if(!isCluster){
            return;
        }
        SDBTestHelper.println("before disable readonly");
        BSONObject detail = dc.getDetail();
        SDBTestHelper.println(detail.toString());
        
        dc.enableReadonly();
        
        SDBTestHelper.println("after enable readonly");
        detail = dc.getDetail();
        Boolean isEnable  = (Boolean)detail.get("Readonly");
        assertEquals(isEnable, Boolean.TRUE);
        SDBTestHelper.println(detail.toString());
        
        dc.disableReadonly();
        
        SDBTestHelper.println("after disable readonly");
        detail = dc.getDetail();
        isEnable  = (Boolean)detail.get("Readonly");
        assertEquals(isEnable, Boolean.FALSE);
        SDBTestHelper.println(detail.toString());
    }
}