package com.staj.staj;

import com.staj.staj.Node.dao.RawDataDAO;
import com.staj.staj.Node.entity.RawData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashSet;
import java.util.Set;

@SpringBootTest
class MainServiceImplTest {
    @Autowired
    private RawDataDAO rawDataDAO;

    @Test
    public void testSaveRawData() {
        Update update = new Update();
        Message msg = new Message();
        msg.setText("ololoTestMessage");
        update.setMessage(msg);

        RawData rawData = RawData.builder()//storing to hash collection
                .event(update)
                .build();
        Set<RawData> testData = new HashSet<>();

        testData.add(rawData);
        rawDataDAO.save(rawData);//storing to DB and setting id

        Assert.isTrue(testData.contains(rawData), "Entity not found in the set");
    }
}
