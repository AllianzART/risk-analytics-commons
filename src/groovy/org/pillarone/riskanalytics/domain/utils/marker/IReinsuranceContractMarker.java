package org.pillarone.riskanalytics.domain.utils.marker;

import org.pillarone.riskanalytics.core.components.IComponentMarker;

/**
 * @author stefan.kunz (at) intuitive-collaboration (dot) com
 */
public interface IReinsuranceContractMarker extends IComponentMarker {

    /**
     * @return typically true for proportional contracts
     */
    boolean isProportionalContract();
}
