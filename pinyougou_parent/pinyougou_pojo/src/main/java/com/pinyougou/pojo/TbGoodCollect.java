package com.pinyougou.pojo;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "tb_good_collect")
public class TbGoodCollect implements Serializable {
    @Id
    @Column(name = "good_id")
    private Long goodId;

    private String username;

    private static final long serialVersionUID = 1L;

    /**
     * @return good_id
     */
    public Long getGoodId() {
        return goodId;
    }

    /**
     * @param goodId
     */
    public void setGoodId(Long goodId) {
        this.goodId = goodId;
    }

    /**
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", goodId=").append(goodId);
        sb.append(", username=").append(username);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}