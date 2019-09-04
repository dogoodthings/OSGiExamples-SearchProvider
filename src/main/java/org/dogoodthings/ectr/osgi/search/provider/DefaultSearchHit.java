
package org.dogoodthings.ectr.osgi.search.provider;

import com.dscsag.plm.spi.interfaces.objects.PlmObjectKey;
import com.dscsag.plm.spi.interfaces.search.SearchHit;

/**
 *
 */
public class DefaultSearchHit implements SearchHit
{
  private PlmObjectKey objectKey;
  private float score;

  public DefaultSearchHit(PlmObjectKey objectKey, float score)
  {
   this.objectKey=objectKey;
   this.score=score;
  }

  @Override
  public PlmObjectKey getObjectKey()
  {
    return objectKey;
  }

  @Override
  public float getScore()
  {
    return score;
  }

}