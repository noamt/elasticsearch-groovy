/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.elasticsearch.groovy.client

import org.elasticsearch.client.AdminClient
import org.elasticsearch.client.ClusterAdminClient
import org.elasticsearch.client.IndicesAdminClient
import org.elasticsearch.groovy.AbstractElasticsearchTestCase
import org.elasticsearch.node.Node

import org.junit.Test

import static org.elasticsearch.node.NodeBuilder.nodeBuilder

import static org.mockito.Mockito.mock
import static org.mockito.Mockito.verifyZeroInteractions
import static org.mockito.Mockito.when

/**
 * Tests {@link AdminClientExtensions}.
 */
class AdminClientExtensionsTests extends AbstractElasticsearchTestCase {
    /**
     * Mock {@link AdminClient} to ensure functionality.
     */
    private final AdminClient admin = mock(AdminClient)

    @Test
    void testGetCluster() {
        ClusterAdminClient cluster = mock(ClusterAdminClient)

        when(admin.cluster()).thenReturn(cluster)

        assert AdminClientExtensions.getCluster(admin) == cluster

        verifyZeroInteractions(cluster)
    }

    @Test
    void testGetIndices() {
        IndicesAdminClient indices = mock(IndicesAdminClient)

        when(admin.indices()).thenReturn(indices)

        assert AdminClientExtensions.getIndices(admin) == indices

        verifyZeroInteractions(indices)
    }

    @Test
    void testExtensionModuleConfigured() {
        Node node = nodeBuilder().local(true).build()
        AdminClient admin = node.client().admin()

        assert admin.getCluster() == admin.cluster()
        assert admin.getIndices() == admin.indices()

        node.close()
    }
}