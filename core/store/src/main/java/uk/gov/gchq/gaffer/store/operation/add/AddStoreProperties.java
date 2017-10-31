/*
 * Copyright 2017 Crown Copyright
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.gchq.gaffer.store.operation.add;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import org.apache.commons.lang3.exception.CloneFailedException;

import uk.gov.gchq.gaffer.commonutil.Required;
import uk.gov.gchq.gaffer.operation.Operation;
import uk.gov.gchq.gaffer.store.StoreProperties;

import java.util.Map;
import java.util.Properties;

/**
 * An Operation used for adding {@link StoreProperties} to the {@link uk.gov.gchq.gaffer.store.library.GraphLibrary} of a store.
 *
 * @see StoreProperties
 */
public class AddStoreProperties implements Operation {

    @Required
    private StoreProperties storeProperties;
    /**
     * A list of storeProperties Id's held within the Library to be retrieved
     * and merged to form a new storeProperties, before be merged with the optional
     * {@link #storeProperties} field.
     */
    private String parentPropertiesId;
    private Map<String, String> options;

    @Override
    public AddStoreProperties shallowClone() throws CloneFailedException {
        return new Builder()
                .storeProperties(storeProperties)
                .parentPropertiesId(parentPropertiesId)
                .options(this.options)
                .build();
    }

    public StoreProperties getStoreProperties() {
        return storeProperties;
    }

    public void setStoreProperties(final StoreProperties properties) {
        this.storeProperties = properties;
    }

    @Override
    public Map<String, String> getOptions() {
        return options;
    }

    @Override
    public void setOptions(final Map<String, String> options) {
        this.options = options;
    }

    @JsonGetter("storeProperties")
    public Properties getProperties() {
        return null != storeProperties ? storeProperties.getProperties() : null;
    }

    @JsonSetter("storeProperties")
    public void setProperties(final Properties properties) {
        if (null == properties) {
            setStoreProperties(null);
        } else {
            setStoreProperties(StoreProperties.loadStoreProperties(properties));
        }
    }

    public String getParentPropertiesId() {
        return parentPropertiesId;
    }

    public void setParentPropertiesId(final String parentPropertiesId) {
        this.parentPropertiesId = parentPropertiesId;
    }

    public static class Builder extends BaseBuilder<AddStoreProperties, Builder> {
        public Builder() {
            super(new AddStoreProperties());
        }

        public Builder storeProperties(final StoreProperties storeProperties) {
            _getOp().setStoreProperties(storeProperties);
            return this;
        }

        public Builder parentPropertiesId(final String parentPropertiesId) {
            this._getOp().setParentPropertiesId(parentPropertiesId);
            return _self();
        }

    }
}