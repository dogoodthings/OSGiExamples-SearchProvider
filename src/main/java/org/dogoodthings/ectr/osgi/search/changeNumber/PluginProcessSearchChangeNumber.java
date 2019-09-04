package org.dogoodthings.ectr.osgi.search.changeNumber;

import java.util.ArrayList;
import java.util.List;

import org.dogoodthings.ectr.osgi.search.PluginProcessRfcSearch;
import org.dogoodthings.ectr.osgi.search.provider.DefaultSearchHit;

import com.dscsag.plm.spi.interfaces.objects.PlmObjectKey;
import com.dscsag.plm.spi.interfaces.rfc.RfcResult;
import com.dscsag.plm.spi.interfaces.rfc.RfcTable;
import com.dscsag.plm.spi.interfaces.search.SearchHit;
import com.dscsag.plm.spi.interfaces.search.SearchQuery;
import com.dscsag.plm.spi.interfaces.search.SearchResult;
import com.dscsag.plm.spi.rfc.builder.RfcCallBuilder;

/*
================================================================================
#55 /DSCSAG/ECM_GETLIST2
================================================================================

 INPUT -------------------------------------------------------------------------

     | NAME                                 | VALUE                                |
     | GETCLASSIFICATION                    |                                      |
     | IV_CLASSNAME                         |                                      |
     | IV_CLASSTYPE                         |                                      |
     | IV_CLASS_KEYDATE                     | 9999-12-30                           |
     | IV_CLIENT_VERSION                    | 4050                                 |
     | IV_DSC_CONV_ID                       | aacf05a5-25eb-490e-9de5-fd7face8c9a5 |
     | IV_ESH_ACTIVE                        |                                      |
     | IV_ESH_SEARCH_TERM                   |                                      |
     | IV_MAXDETAILS                        | 200                                  |
     | IV_MAXROWS                           | 101                                  |
     | IV_PACKAGE_SIZE                      | 5000                                 |
     | IV_RECURSIVE                         |                                      |


    TABLE: IT_AENR_SEL (/DSCSAG/ECM_NUMBER_SEL) , 1 row(s)
           | SIGN | OPTION | CHANGE_NUMBER_LOW | CHANGE_NUMBER_HIGH |
           | I    | EQ     | SEBASTIAN         |                    |

    TABLE: IT_AETXT_SEL (/DSCSAG/RANGES_AETXT) , 1 row(s)
           | SIGN | OPTION | LOW        | HIGH |
           | I    | CP     | descriptio |      |

 */
public abstract class PluginProcessSearchChangeNumber extends PluginProcessRfcSearch
{
  @Override
  protected RfcCallBuilder prepareRfcCallBuilder(SearchQuery searchQuery)
  {
    RfcCallBuilder rfcCallBuilder = new RfcCallBuilder("/DSCSAG/ECM_GETLIST2");
    rfcCallBuilder.setInputParameter("GETCLASSIFICATION", " ");
    rfcCallBuilder.setInputParameter("IV_MAXDETAILS", "0");
    rfcCallBuilder.setInputParameter("IV_MAXROWS", String.valueOf(searchQuery.getMaxHits()));
    rfcCallBuilder.setInputParameter("IV_RECURSIVE", " ");
    return rfcCallBuilder;
  }

  @Override
  protected SearchResult createResult(RfcResult rfcResult)
  {
    List<SearchHit> searchResult = new ArrayList<>();
    RfcTable mats = rfcResult.getTable("ET_MORE_ECMNUMS");
    for (int i = 0; i < mats.getRowCount(); i++)
      searchResult.add(new DefaultSearchHit(new PlmObjectKey("AENR", mats.getRow(i).getFieldValue("CHANGE_NUMBER")), 1.0f));
    return () -> searchResult;
  }
}
