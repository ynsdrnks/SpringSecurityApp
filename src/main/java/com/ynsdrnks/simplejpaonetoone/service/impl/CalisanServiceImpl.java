package com.ynsdrnks.simplejpaonetoone.service.impl;

import com.ynsdrnks.simplejpaonetoone.entity.Calisan;
import com.ynsdrnks.simplejpaonetoone.repository.CalisanRepository;
import com.ynsdrnks.simplejpaonetoone.service.CalisanService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalisanServiceImpl implements CalisanService {

    @Qualifier("calisanRepo")
    @Autowired
    private CalisanRepository repo;

    @Override
    public List<Calisan> listAllCalisans() {
    return repo.findAll();
    }

    @Override
    public void save(Calisan calisan) {
    repo.save(calisan);
    }

    @Override
    public Calisan getByCalisanId(Long id) {
        return repo.getOne(id);
    }

    @Override
    public void deleteById(Long id) {
    repo.deleteById(id);
    }
}

