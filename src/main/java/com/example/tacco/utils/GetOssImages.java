package com.example.tacco.utils;

import com.aliyun.oss.*;
import com.aliyun.oss.model.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.List;

public class GetOssImages {
    private static String endpoint = "https://oss-cn-guangzhou.aliyuncs.com";
    // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
    private static String accessKeyId = "LTAI5tSHUDfXDLDZCaxB6Q6V";
    private static String accessKeySecret = "DyVQS9cdqGccddNggPIuR7RSQLO3M4";
    // 填写Bucket名称，例如examplebucket。
    private static String bucketName = "scau404";

    private static String keyPrefix = "camera1/";

    private static List<OSSObjectSummary> sums;

    public static void main() throws Exception {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 填写Object完整路径后，根据SELECT语句查询文件中的数据。Object完整路径中不能包含Bucket名称。
        // 填写CSV格式的Object完整路径。
        selectCsvSample("test.csv", ossClient);
        selectImage(ossClient);
        ossClient.shutdown();
    }

    public List<OSSObjectSummary> getImages() throws Exception {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        selectImage(ossClient);
        return sums;
    }

    private static void selectImage(OSS ossClient) throws Exception {
        try {
            // 列举文件。如果不设置keyPrefix，则列举存储空间下的所有文件。如果设置keyPrefix，则列举包含指定前缀的文件。
            ObjectListing objectListing = ossClient.listObjects(bucketName, keyPrefix);
            sums = objectListing.getObjectSummaries();
            int count = 0;
            List<Bucket> bucket = ossClient.listBuckets();
            for (Bucket b : bucket) {
                System.out.println(b.getName());
            }
            for (OSSObjectSummary s : sums) {
                s.setBucketName(s.getKey());
                s.setKey("https://scau404.oss-cn-guangzhou.aliyuncs.com/".concat(s.getKey()));
                count++;
            }
            System.out.println("示例：" + sums.get(0));
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }

    }

    private static void selectCsvSample(String key, OSS ossClient) throws Exception {
        // 填写上传的内容。
        String content = "name,school,company,age\r\n" +
                "Lora Francis,School A,Staples Inc,27\r\n" +
                "Eleanor Little,School B,\"Conectiv, Inc\",43\r\n" +
                "Rosie Hughes,School C,Western Gas Resources Inc,44\r\n" +
                "Lawrence Ross,School D,MetLife Inc.,24";

        ossClient.putObject(bucketName, key, new ByteArrayInputStream(content.getBytes()));


        SelectObjectMetadata selectObjectMetadata = ossClient.createSelectObjectMetadata(
                new CreateSelectObjectMetadataRequest(bucketName, key)
                        .withInputSerialization(
                                new InputSerialization().withCsvInputFormat(
                                        // 填写内容中不同记录之间的分隔符，例如\r\n。
                                        new CSVFormat().withHeaderInfo(CSVFormat.Header.Use).withRecordDelimiter("\r\n"))));
        System.out.println(selectObjectMetadata.getCsvObjectMetadata().getTotalLines());
        System.out.println(selectObjectMetadata.getCsvObjectMetadata().getSplits());

        SelectObjectRequest selectObjectRequest =
                new SelectObjectRequest(bucketName, key)
                        .withInputSerialization(
                                new InputSerialization().withCsvInputFormat(
                                        new CSVFormat().withHeaderInfo(CSVFormat.Header.Use).withRecordDelimiter("\r\n")))
                        .withOutputSerialization(new OutputSerialization().withCsvOutputFormat(new CSVFormat()));
        // 使用SELECT语句查询第4列，值大于40的所有记录。
        selectObjectRequest.setExpression("select * from ossobject where _4 > 40");
        OSSObject ossObject = ossClient.selectObject(selectObjectRequest);

        // 读取内容。
        BufferedReader reader = new BufferedReader(new InputStreamReader(ossObject.getObjectContent()));
        while (true) {
            String line = reader.readLine();
            if (line == null) {
                break;
            }
            System.out.println(line);
        }
        reader.close();

        ossClient.deleteObject(bucketName, key);
    }


}
