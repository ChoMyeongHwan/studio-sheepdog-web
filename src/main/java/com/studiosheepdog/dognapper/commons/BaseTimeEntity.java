package com.studiosheepdog.dognapper.commons;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass //JPA가 공통 매핑 정보를 상속할 수 있게 해줌
@EntityListeners(AuditingEntityListener.class) //해당 엔티티에 대해 AuditingEntityListener를 사용하도록 설정
@Getter
public abstract class BaseTimeEntity {
    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    // 테스트 환경에서만 사용
    public void setTestTime(LocalDateTime time) {
        this.createdDate = time;
        this.modifiedDate = time;
    }
}
