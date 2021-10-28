package completablefuture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 类作用描述
 *
 * @author 江峰
 * @since 2021/10/28
 */
public class CompletableFutureDemo {

    class StockOrderItemInfo {

    }

    public static void main(String[] args) {
        // 查询订单详情
        List<CompletableFuture<List<StockOrderItemInfo>>> futureList = new ArrayList<>();
        AtomicInteger index = new AtomicInteger();
        List<Integer> stockOrderIdList = new ArrayList<>();
        stockOrderIdList.forEach((tempStockOrderIdList) -> {
            // log.info("collection = indexAndStockOrderIdListMap, index = [{}], size = [{}]", index.getAndIncrement(), tempStockOrderIdList.size());
            // futureList.add(CompletableFuture.supplyAsync(() -> stockOrderItemService.findByStockOrderIdList(tempStockOrderIdList), commonTaskExecutor));
        });

        // 多线程都执行完毕后的结果集
        List<List<StockOrderItemInfo>> futureResultList = futureList.stream().map(CompletableFuture::join).collect(Collectors.toList());

        // 订单详情列表
        List<StockOrderItemInfo> stockOrderItemInfoList = new ArrayList<>();
        futureResultList.forEach(stockOrderItemInfoList::addAll);
    }
}
