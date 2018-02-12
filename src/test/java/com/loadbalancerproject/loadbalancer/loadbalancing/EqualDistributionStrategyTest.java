package com.loadbalancerproject.loadbalancer.loadbalancing;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManagerFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

public class EqualDistributionStrategyTest {

    @Mock
    LoadCache loadCache;

    @Mock
    EntityManagerFactory emf1;

    @Mock
    EntityManagerFactory emf2;

    @Mock
    EntityManagerFactory emf3;

    LoadBalancingStrategy strategy;

    Collection<EntityManagerFactory> emfs;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        strategy = new EqualDistributionStrategy(loadCache);
        emfs = new HashSet<>(Arrays.asList(emf1, emf2, emf3));
    }

    @Test
    public void shouldChooseEMFWithLowestLoad() throws Exception {
        // given
        when(loadCache.getLoad(emf1)).thenReturn(5);
        when(loadCache.getLoad(emf2)).thenReturn(4);
        when(loadCache.getLoad(emf3)).thenReturn(5);

        // when
        EntityManagerFactory chosen = strategy.chooseEntityManagerFactory(emfs);

        // then
        assertThat(chosen).isEqualTo(emf2);
    }
}