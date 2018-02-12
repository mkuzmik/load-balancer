package com.loadbalancerproject.loadbalancer.loadbalancing;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManagerFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class LoadCacheTest {

    @Mock
    private EntityManagerFactory emf1;

    @Mock
    private EntityManagerFactory emf2;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnZeroWhenNoLoadRegistered() throws Exception {
        LoadCache loadCache = LoadCache.getInstance();

        assertThat(loadCache.getLoad(emf1)).isZero();
    }

    @Test
    public void shouldRegisterLoad() throws Exception {
        LoadCache loadCache = LoadCache.getInstance();

        // when
        loadCache.load(emf1);
        loadCache.load(emf1);
        loadCache.load(emf2);

        // then
        assertThat(loadCache.getLoad(emf1)).isEqualTo(2);
        assertThat(loadCache.getLoad(emf2)).isEqualTo(1);
    }

    @Test
    public void shouldReleaseLoad() throws Exception {
        LoadCache loadCache = LoadCache.getInstance();

        // when
        loadCache.load(emf1);
        loadCache.load(emf1);
        loadCache.release(emf1);

        // then
        assertThat(loadCache.getLoad(emf1)).isEqualTo(1);
    }

    @Test
    public void shouldNotReleaseLoadWhenLoadIsZero() throws Exception {
        LoadCache loadCache = LoadCache.getInstance();

        // when
        loadCache.release(emf1);

        // then
        assertThat(loadCache.getLoad(emf1)).isEqualTo(0);
    }
}