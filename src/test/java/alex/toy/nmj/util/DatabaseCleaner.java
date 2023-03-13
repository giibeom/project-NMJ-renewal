package alex.toy.nmj.util;

import com.google.common.base.CaseFormat;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Profile("test")
@Component
public class DatabaseCleaner implements InitializingBean {

    @PersistenceContext
    private EntityManager entityManager;

    private List<String> tableNames;

    private static final String TABLE_PREFIX = "NMJ_";

    /**
     * 엔티티 정보를 순회하여 테이블명을 추출합니다.
     */
    @Override
    public void afterPropertiesSet() {
        tableNames = entityManager.getMetamodel().getEntities().stream()
                .filter(entity -> entity.getJavaType().getAnnotation(Entity.class) != null)
                .map(entity -> TABLE_PREFIX + CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, entity.getName()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void execute() {
        // 영속성 컨텍스트의 쓰기 지연 저장소에 있는 SQL 모두 적용
        entityManager.flush();

        // 참조(fk) 무결성 비활성화 : 연관 관계 맵핑된 테이블이 있는 경우 참조 무결성을 해제 해주어야 TRUNCATE가 가능하다.
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();

        // 테이블을 순회하면서 TRUNCATE SQL문 수행
        for (String tableName : tableNames) {
            // H2 database에서 PK를 초기화하려면 TRUNCATE 명령문에 RESTART IDENTITY를 추가해야 함
            // ref: https://www.h2database.com/html/commands.html?highlight=truncate&search=truncate#truncate_table
            entityManager.createNativeQuery("TRUNCATE TABLE " + tableName + " RESTART IDENTITY").executeUpdate();

            // MySQL 기준 TRUNCATE 진행 시 별다른 명령문이 없어도 AUTO_INCREMENT 값은 시작 값으로 자동 재설정 (5.7, 8.0 동일)
            // ref : https://dev.mysql.com/doc/refman/5.7/en/truncate-table.html
        }

        // 참조(fk) 무결성 활성화
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
    }
}
