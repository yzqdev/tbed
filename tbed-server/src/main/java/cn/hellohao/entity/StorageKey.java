package cn.hellohao.entity;

/**
 * 存储键
 *
 * @author yanni
 * @date 2021/11/20
 */
public class StorageKey {
    private Integer id;
    private String accessKey;
    private String accessSecret;
    private String endpoint;
    private String bucketName;
    private String requestAddress;
    private Integer storageType;
    private String keyName;

    public StorageKey() {
        super();
    }

    public StorageKey(Integer id, String accessKey, String accessSecret, String endpoint, String bucketName,
                      String requestAddress, Integer storageType, String keyName) {
        super();
        this.id = id;
        this.accessKey = accessKey;
        this.accessSecret = accessSecret;
        this.endpoint = endpoint;
        this.bucketName = bucketName;
        this.requestAddress = requestAddress;
        this.storageType = storageType;
        this.keyName = keyName;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getAccessSecret() {
        return accessSecret;
    }

    public void setAccessSecret(String accessSecret) {
        this.accessSecret = accessSecret;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getRequestAddress() {
        return requestAddress;
    }

    public void setRequestAddress(String requestAddress) {
        this.requestAddress = requestAddress;
    }

    public Integer getStorageType() {
        return storageType;
    }

    public void setStorageType(Integer storageType) {
        this.storageType = storageType;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }
}
