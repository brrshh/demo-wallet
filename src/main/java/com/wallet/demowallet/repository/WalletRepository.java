package com.wallet.demowallet.repository;

import com.wallet.demowallet.domain.Wallet;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.Optional;

@Repository
public interface WalletRepository extends CrudRepository<Wallet, Integer> {

    @Modifying
    @Query(value = "SET SESSION CHARACTERISTICS AS TRANSACTION ISOLATION LEVEL SERIALIZABLE", nativeQuery = true)
    void setIsolation();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    default Optional<Wallet> findByIdLock(Integer id) {
        return findById(id);
    }
}