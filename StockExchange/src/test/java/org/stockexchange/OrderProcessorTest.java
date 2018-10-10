package org.stockexchange;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.stockexchange.inout.OrderRecord;
import org.stockexchange.inout.reader.IFileReader;
import org.stockexchange.inout.rowmapper.OrderRowMapper;
import org.stockexchange.model.Bank;
import org.stockexchange.model.fininstrument.FinancialInstrument;
import org.stockexchange.model.fininstrument.FinancialInstrumentType;
import org.stockexchange.service.BankService;
import org.stockexchange.service.DealProcessor;
import org.stockexchange.service.matching.Deal;
import org.stockexchange.service.matching.OrderMatcher;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.stockexchange.inout.writer.FileWriter.writeFile;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class OrderProcessorTest {
    @Autowired
    private IFileReader fileReader;
    @Autowired
    private FinancialInstrumentType financialInstrumentType;
    @Autowired
    private OrderRowMapper orderRowMapper;
    @Autowired
    private Bank bank;
    @Autowired
    private DealProcessor dealProcessor;
    @Autowired
    private OrderMatcher orderMatcher;
    @Autowired
    private BankService bankService;

    private Path testRootPath = Paths.get("src/test/resources");

    @Test
    public void testSimple() throws Exception {
        Path testDir = testRootPath.resolve("example-1");
        Path finInstrumentPath = testDir.resolve("clients-info-mapper.txt");
        Path clientPath = testDir.resolve("clients.txt");
        Path orderPath = testDir.resolve("orders.txt");

        startTest(finInstrumentPath, clientPath, orderPath);
    }

    @Test
    public void testDoNoBuyFromYourself() throws Exception {
        Path testDir = testRootPath.resolve("example-2");
        Path finInstrumentPath = testDir.resolve("clients-info-mapper.txt");
        Path clientPath = testDir.resolve("clients.txt");
        Path orderPath = testDir.resolve("orders.txt");

        startTest(finInstrumentPath, clientPath, orderPath);
    }


    @Test
    public void testFull() throws Exception {
        Path testDir = testRootPath.resolve("full-example");
        Path finInstrumentPath = testDir.resolve("clients-info-mapper.txt");
        Path clientPath = testDir.resolve("clients.txt");
        Path orderPath = testDir.resolve("orders.txt");

        startTest(finInstrumentPath, clientPath, orderPath);
    }

    public void startTest(Path finInstrumentPath, Path clientPath, Path orderPath) throws Exception {
        bankService.setup(finInstrumentPath, clientPath);

        // расчет баланса до начала мапинга заявок и обработки сделок
        // для последующего сравнения с балансом после окончания обработки
        HashMap<FinancialInstrument, Integer> allBalancesBefore = new HashMap<>();
        for (FinancialInstrument fi : financialInstrumentType.getFinancialInstruments().values()) {
            int sum = bank.getClientAccountMap().values().stream()
                    .mapToInt(a -> a.getFinInstrumentBalance(fi)).sum();
            allBalancesBefore.put(fi, sum);
        }

        // зачитывание и маппинг заявок
        Stream<OrderRecord> orderStream =
                fileReader.readLines(orderPath).map(orderRowMapper::from);
        List<Deal> dealList = orderMatcher.processOrderStream(orderStream);

        // обработка сделок
        dealList.stream().limit(1).forEach(dealProcessor::processDeal);

        // не обрабатывать продажи и покупки самому себе
        Assert.assertFalse(dealList.stream()
                        .filter(d -> d.getBuyer() == d.getSeller())
                        .findFirst().isPresent());

        // сравниваем с исходным балансом
        for (FinancialInstrument fi : financialInstrumentType.getFinancialInstruments().values()) {
            int sum = bank.getClientAccountMap().values().stream()
                    .mapToInt(a -> a.getFinInstrumentBalance(fi)).sum();
            Assert.assertTrue(allBalancesBefore.get(fi).equals(sum));
        }

        // запись результирующего состояния счетов по каждому клиенту
        Path matchedPath = clientPath.resolveSibling("matched.txt");
        writeFile(dealList.stream().map(Deal::getInfo)
                .collect(Collectors.toList()), matchedPath.toFile());

        // запись состоявшихся сделок
        Path resultPath = clientPath.resolveSibling("result.txt");
        writeFile(bankService.getAccountList(), resultPath.toFile());
    }

}

