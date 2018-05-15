package  com.cch.cz.mapper.provider;
import com.cch.cz.base.dao.BuildSql;
import com.cch.cz.base.dao.provider.BaseProvider;
import com.cch.cz.entity.City;
import com.cch.cz.entity.CityTypeEnum;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.LoggerFactory;

public class CityProvider extends BaseProvider<com.cch.cz.entity.City,java.lang.Long> {
    org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    public String privince(){
        return new SQL(){
            {
            SELECT(BuildSql.select(City.class));
            FROM(BuildSql.tablename(City.class));
            WHERE(" type ="+ CityTypeEnum.PROVINCE);
            }
        }.toString();
    }

    public String city(City city){
        return new SQL(){{
            SELECT(BuildSql.select(City.class));
            FROM(BuildSql.tablename(City.class));
            WHERE( "p_id=#{id}");
        }}.toString();
    }

}