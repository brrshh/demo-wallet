package com.wallet.demowallet.repository;

import com.wallet.demowallet.domain.Wallet;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends CrudRepository<Wallet, Integer> {

    @Modifying
    @Query(value = "SET SESSION CHARACTERISTICS AS TRANSACTION ISOLATION LEVEL SERIALIZABLE", nativeQuery = true)
    void setIsolation();

    @Query(value = "SELECT * From Wallet w where w.id =?1 FOR UPDATE;", nativeQuery = true)
    Optional<Wallet> findByIdLock(Integer id);
}