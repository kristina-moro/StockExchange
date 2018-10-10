package org.stockexchange;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.stockexchange.inout.OrderRecord;
import org.stockexchange.inout.reader.IFileReader;
import org.stockexchange.inout.rowmapper.OrderRowMapper;
import org.stockexchange.service.BankService;
import org.stockexchange.service.DealProcessor;
import org.stockexchange.service.matching.Deal;
import org.stockexchange.service.matching.OrderMatcher;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.stockexchange.inout.writer.FileWriter.writeFile;


@Component
public class Start {
    @Autowired
    private IFileReader fileReader;
    @Autowired
    private OrderRowMapper orderRowMapper;
    @Autowired
    private DealProcessor dealProcessor;
    @Autowired
    private OrderMatcher orderMatcher;
    @Autowired
    private BankService bankService;

    public void start(Path finInstrumentPath, Path clientPath, Path orderPath) throws Exception {
        bankService.setup(finInstrumentPath, clientPath);

        // зачитывание и маппинг заявок
        Stream<OrderRecord> orderStream =
                fileReader.readLines(orderPath).map(orderRowMapper::from);
        List<Deal> dealList = orderMatcher.processOrderStream(orderStream);

        // обработка сделок
        dealList.stream().limit(1).forEach(dealProcessor::processDeal);

        // запись результирующего состояния счетов по каждому клиенту
        Path matchedPath = clientPath.resolveSibling("matched.txt");
        writeFile(dealList.stream().map(Deal::getInfo)
                .collect(Collectors.toList()), matchedPath.toFile());

        // запись состоявшихся сделок
        Path resultPath = clientPath.resolveSibling("result.txt");
        writeFile(bankService.getAccountList(), resultPath.toFile());
    }

}
