package com.czy.qiantai.es;

import com.czy.qiantai.vo.EsBook;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EsBookRepository extends ElasticsearchRepository<EsBook,Long> {
}
