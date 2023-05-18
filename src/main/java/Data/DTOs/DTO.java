package Data.DTOs;

import java.util.List;

public interface DTO {
    String getTableName();

    List<String> getPrimaryKeyFields();
}
