package com.xxmrk888ytxx.observer.domain.BillingManager

import android.app.Activity
import android.content.Context
import com.android.billingclient.api.AcknowledgePurchaseParams
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.PendingPurchasesParams
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.QueryPurchasesParams
import com.xxmrk888ytxx.coredeps.ApplicationScope
import com.xxmrk888ytxx.coredeps.SharedInterfaces.BillingManager
import com.xxmrk888ytxx.coredeps.SharedInterfaces.PurchaseCallback.PurchaseListenerManager
import com.xxmrk888ytxx.coredeps.ifNotNull
import com.xxmrk888ytxx.coredeps.logcatMessageD
import com.xxmrk888ytxx.observer.DI.AppScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AppScope
internal class BillingManagerImpl @Inject constructor(
    private val context: Context,

    private val purchaseListenerManager: PurchaseListenerManager
) : BillingManager {

    private val productIdMap: MutableMap<String, ProductDetails?> = mutableMapOf(
        supportOn5ProductId to null,
        supportOn10ProductId to null,
        supportOn15ProductId to null
    )

    private val purchasesUpdatedListener by lazy {
        PurchasesUpdatedListener { _, purchases ->
            purchases.ifNotNull { verifyPurchase(this) }
        }
    }

    private val billingClient by lazy {
        BillingClient.newBuilder(context)
            .setListener(purchasesUpdatedListener)
            .enablePendingPurchases(PendingPurchasesParams.newBuilder().enableOneTimeProducts().build())
            .build()
    }


    private fun verifyPurchase(purchases: List<Purchase>) {
        purchases.forEach {
            val acknowledgePurchaseParams = AcknowledgePurchaseParams
                .newBuilder()
                .setPurchaseToken(it.purchaseToken)
                .build()

            billingClient.acknowledgePurchase(acknowledgePurchaseParams) { billingClient ->
                if (billingClient.responseCode == BillingClient.BillingResponseCode.OK) {

                }
            }
        }
    }

    override fun connectToGooglePlay() {
        try {
            billingClient.startConnection(object : BillingClientStateListener {
                override fun onBillingSetupFinished(billingResult: BillingResult) {
                    if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                        ApplicationScope.launch { requestProducts() }
                    }
                }

                override fun onBillingServiceDisconnected() {
                    connectToGooglePlay()
                }
            })
        } catch (e: Exception) {
            logcatMessageD("Error connection to Google Play ${e.stackTraceToString()}")
        }
    }

    private fun requestProducts() {
        val requestedProducts = productIdMap.map {
            QueryProductDetailsParams.Product.newBuilder()
                .setProductId(it.key)
                .setProductType(BillingClient.ProductType.INAPP)
                .build()
        }

        val params = QueryProductDetailsParams.newBuilder()
            .setProductList(requestedProducts)
            .build()

        billingClient.queryProductDetailsAsync(params) { _, prodDetailsList ->
            prodDetailsList.productDetailsList.forEach {
                if (productIdMap.containsKey(it.productId)) {
                    productIdMap[it.productId] = it
                }
            }
        }
    }

    override fun buyDeveloperSupportOn5Dollars(activity: Activity) {
        productIdMap[supportOn5ProductId].ifNotNull {
            sendBuyRequest(this, activity)
        }
    }

    override fun buyDeveloperSupportOn10Dollars(activity: Activity) {
        productIdMap[supportOn10ProductId].ifNotNull {
            sendBuyRequest(this, activity)
        }
    }

    override fun buyDeveloperSupportOn15Dollars(activity: Activity) {
        productIdMap[supportOn15ProductId].ifNotNull {
            sendBuyRequest(this, activity)
        }
    }

    private fun sendBuyRequest(product: ProductDetails, activity: Activity) {
        val productDetailsParamsList = listOf(
            BillingFlowParams.ProductDetailsParams.newBuilder()
                .setProductDetails(product)
                .build()
        )

        val billingFlowParams = BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(productDetailsParamsList)
            .build()

        billingClient.launchBillingFlow(activity, billingFlowParams)
    }

    override fun restorePurchases() {
        billingClient.queryPurchasesAsync(
            QueryPurchasesParams.newBuilder().setProductType(BillingClient.ProductType.INAPP)
                .build()
        ) { billingResult: BillingResult, purchases: List<Purchase> ->
            if (billingResult.responseCode != BillingClient.BillingResponseCode.OK) return@queryPurchasesAsync
            purchases.forEach {
                if (it.purchaseState == Purchase.PurchaseState.PURCHASED) {
                    verifyPurchase(listOf(it))
                }
            }
        }
    }

    companion object {
        const val supportOn5ProductId = "support_on_5"
        const val supportOn10ProductId = "support_on_10"
        const val supportOn15ProductId = "support_on_15"
    }
}