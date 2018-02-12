package com.loadbalancerproject.loadbalancer.loadbalancing;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManagerFactory;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

public class RandomStrategyTest {

    @Mock
    EntityManagerFactory emf1;

    LoadBalancingStrategy strategy;

    Collection<EntityManagerFactory> emfs;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        strategy = new RandomStrategy();
        emfs = new HashSet<>(Arrays.asList(emf1));
    }

    @Test
    public void shouldChooseEmfWhenOnlyOne() throws Exception {

        // when
        EntityManagerFactory chosen = strategy.chooseEntityManagerFactory(emfs);

        // then
        assertThat(chosen).isEqualTo(emf1);
    }
}