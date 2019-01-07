package code.solrj.test;

import code.solrj.entity.Product;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.junit.Test;

import java.util.List;

public class SolrTest {
    /** 定义SolrServer，用来操作Solr */
    private SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr/collection1");
    /** 添加或修改索引 */
    @Test
    public void saveOrUpdate() throws Exception {
        Product product = new Product();
        product.setPid("8000");
        product.setName("iphone8");
        product.setCatalogName("手机");
        product.setPrice(8000);
        product.setDescription("苹果手机就是垃圾！");
        product.setPicture("1212.jpg");
        solrServer.addBean(product);
        solrServer.commit();
    }

    /** 根据id删除索引 */
    @Test
    public void deleteById() throws Exception {
        solrServer.deleteById("8000");
        solrServer.commit();
    }

    /** 根据条件删除 */
    @Test
    public void deleteByQuery() throws Exception{
        solrServer.deleteByQuery("*:*");
        solrServer.commit();
    }

    /** 查询全部索引 */
    @Test
    public void query() throws Exception{
        // 创建SolrQuery封装查询条件
        SolrQuery sq = new SolrQuery("*:*");
        // 设置分页开始记录数
        sq.setStart(10);
        // 设置每页显示记录数
        sq.setRows(10);
        // 执行搜索，得到查询响应对象
        QueryResponse response = solrServer.query(sq);
        System.out.println("搜索到的总数量：" + response.getResults().getNumFound());
        // 获取搜索结果，并转化成实体集合
        List<Product> products = response.getBeans(Product.class);
        for (Product product : products){
            System.out.println("---------------------");
            System.out.println(product.getPid() + "\t" + product.getName() + "\t" + product.getCatalogName());
        }
    }
}
