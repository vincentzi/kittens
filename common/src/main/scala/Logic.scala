package org.preownedkittens

object Logic {
  def matchLikelihood(kitten: Kitten, buyer: BuyerPreferences): Double = {
    val matches = buyer.attributes.map { attribute =>
        kitten.attributes.contains(attribute)
    }

    val scores = matches map { matched =>
        if (matched) 1.0
        else 0.0
    }

    scores.sum / scores.length  
  }
}
