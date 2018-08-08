package nd.fmnc.pushapns;

import nd.fmnc.pushapns.entities.Push;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by dscottnull on 7/24/18.
 */
public interface PushRepository extends CrudRepository<Push, String> {
    List<Push> findAllByTimeSentIsNull(Pageable limit);
    List<Push> findTop20ByTimeSentIsNull();
}
