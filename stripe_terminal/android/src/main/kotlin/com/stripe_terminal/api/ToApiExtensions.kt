package com.stripe_terminal.api

import com.stripe_terminal.toHashMap
import com.stripe.stripeterminal.external.models.*

fun TerminalException.toApi(): TerminalExceptionApi {
    return TerminalExceptionApi(
        rawCode = errorCode.name,
        message = errorMessage,
        details = stackTraceToString()
    )
}

fun Reader.toApi(): ReaderApi {
    return ReaderApi(
        locationStatus = locationStatus.toApi(),
        batteryLevel = batteryLevel?.toDouble() ?: -1.0,
        deviceType = deviceType.toApi(),
        simulated = isSimulated,
        availableUpdate = availableUpdate?.hasFirmwareUpdate ?: false,
        locationId = location?.id,
        label = label,
        serialNumber = serialNumber!!,
    )
}

fun LocationStatus.toApi(): LocationStatusApi? {
    return when (this) {
        LocationStatus.UNKNOWN -> null
        LocationStatus.SET -> LocationStatusApi.SET
        LocationStatus.NOT_SET -> LocationStatusApi.NOT_SET
    }
}

fun DeviceType.toApi(): DeviceTypeApi? {
    return when (this) {
        DeviceType.CHIPPER_1X -> DeviceTypeApi.CHIPPER1_X
        DeviceType.CHIPPER_2X -> DeviceTypeApi.CHIPPER2_X
        DeviceType.STRIPE_M2 -> DeviceTypeApi.STRIPE_M2
        DeviceType.COTS_DEVICE -> DeviceTypeApi.COTS_DEVICE
        DeviceType.VERIFONE_P400 -> DeviceTypeApi.VERIFONE_P400
        DeviceType.WISECUBE -> DeviceTypeApi.WISE_CUBE
        DeviceType.WISEPAD_3 -> DeviceTypeApi.WISE_PAD3
        DeviceType.WISEPAD_3S -> DeviceTypeApi.WISE_PAD3S
        DeviceType.WISEPOS_E -> DeviceTypeApi.WISE_POS_E
        DeviceType.WISEPOS_E_DEVKIT -> DeviceTypeApi.WISE_POS_E_DEVKIT
        DeviceType.ETNA -> DeviceTypeApi.ETNA
        DeviceType.STRIPE_S700 -> DeviceTypeApi.STRIPE_S700
        DeviceType.STRIPE_S700_DEVKIT -> DeviceTypeApi.STRIPE_S700_DEVKIT
        DeviceType.UNKNOWN -> null
    }
}

fun ConnectionStatus.toApi(): ConnectionStatusApi {
    return when (this) {
        ConnectionStatus.NOT_CONNECTED -> ConnectionStatusApi.NOT_CONNECTED
        ConnectionStatus.CONNECTING -> ConnectionStatusApi.CONNECTING
        ConnectionStatus.CONNECTED -> ConnectionStatusApi.CONNECTED
    }
}

fun ReaderEvent.toApi(): ReaderEventApi {
    return when (this) {
        ReaderEvent.CARD_INSERTED -> ReaderEventApi.CARD_INSERTED
        ReaderEvent.CARD_REMOVED -> ReaderEventApi.CARD_REMOVED
    }
}

fun ReaderDisplayMessage.toApi(): ReaderDisplayMessageApi {
    return when (this) {
        ReaderDisplayMessage.CHECK_MOBILE_DEVICE -> ReaderDisplayMessageApi.CHECK_MOBILE_DEVICE
        ReaderDisplayMessage.RETRY_CARD -> ReaderDisplayMessageApi.RETRY_CARD
        ReaderDisplayMessage.INSERT_CARD -> ReaderDisplayMessageApi.INSERT_CARD
        ReaderDisplayMessage.INSERT_OR_SWIPE_CARD -> ReaderDisplayMessageApi.INSERT_OR_SWIPE_CARD
        ReaderDisplayMessage.SWIPE_CARD -> ReaderDisplayMessageApi.SWIPE_CARD
        ReaderDisplayMessage.REMOVE_CARD -> ReaderDisplayMessageApi.REMOVE_CARD
        ReaderDisplayMessage.MULTIPLE_CONTACTLESS_CARDS_DETECTED -> ReaderDisplayMessageApi.MULTIPLE_CONTACTLESS_CARDS_DETECTED
        ReaderDisplayMessage.TRY_ANOTHER_READ_METHOD -> ReaderDisplayMessageApi.TRY_ANOTHER_READ_METHOD
        ReaderDisplayMessage.TRY_ANOTHER_CARD -> ReaderDisplayMessageApi.TRY_ANOTHER_CARD
        ReaderDisplayMessage.CARD_REMOVED_TOO_EARLY -> ReaderDisplayMessageApi.CARD_REMOVED_TOO_EARLY
    }
}

fun ReaderInputOptions.ReaderInputOption.toApi(): ReaderInputOptionApi? {
    return when (this) {
        ReaderInputOptions.ReaderInputOption.NONE -> null
        ReaderInputOptions.ReaderInputOption.INSERT -> ReaderInputOptionApi.INSERT_CARD
        ReaderInputOptions.ReaderInputOption.SWIPE -> ReaderInputOptionApi.SWIPE_CARD
        ReaderInputOptions.ReaderInputOption.TAP -> ReaderInputOptionApi.TAP_CARD
        ReaderInputOptions.ReaderInputOption.MANUAL_ENTRY -> ReaderInputOptionApi.MANUAL_ENTRY
    }
}

fun BatteryStatus.toApi(): BatteryStatusApi? {
    return when (this) {
        BatteryStatus.UNKNOWN -> null
        BatteryStatus.CRITICAL -> BatteryStatusApi.CRITICAL
        BatteryStatus.LOW -> BatteryStatusApi.LOW
        BatteryStatus.NOMINAL -> BatteryStatusApi.NOMINAL
    }
}

fun ReaderSoftwareUpdate.toApi(): ReaderSoftwareUpdateApi {
    return ReaderSoftwareUpdateApi(
        components = components.map { it.toApi() },
        keyProfileName = keyProfileName,
        onlyInstallRequiredUpdates = onlyInstallRequiredUpdates,
        requiredAt = requiredAt.time,
        settingsVersion = settingsVersion,
        timeEstimate = timeEstimate.toApi(),
        version = version,
    )
}

fun ReaderSoftwareUpdate.UpdateComponent.toApi(): UpdateComponentApi {
    return when (this) {
        ReaderSoftwareUpdate.UpdateComponent.INCREMENTAL -> UpdateComponentApi.INCREMENTAL
        ReaderSoftwareUpdate.UpdateComponent.FIRMWARE -> UpdateComponentApi.FIRMWARE
        ReaderSoftwareUpdate.UpdateComponent.CONFIG -> UpdateComponentApi.CONFIG
        ReaderSoftwareUpdate.UpdateComponent.KEYS -> UpdateComponentApi.KEYS
    }
}

fun ReaderSoftwareUpdate.UpdateTimeEstimate.toApi(): UpdateTimeEstimateApi {
    return when (this) {
        ReaderSoftwareUpdate.UpdateTimeEstimate.LESS_THAN_ONE_MINUTE -> UpdateTimeEstimateApi.LESS_THAN_ONE_MINUTE
        ReaderSoftwareUpdate.UpdateTimeEstimate.ONE_TO_TWO_MINUTES -> UpdateTimeEstimateApi.ONE_TO_TWO_MINUTES
        ReaderSoftwareUpdate.UpdateTimeEstimate.TWO_TO_FIVE_MINUTES -> UpdateTimeEstimateApi.TWO_TO_FIVE_MINUTES
        ReaderSoftwareUpdate.UpdateTimeEstimate.FIVE_TO_FIFTEEN_MINUTES -> UpdateTimeEstimateApi.FIVE_TO_FIFTEEN_MINUTES
    }
}

fun PaymentMethod.toApi(): PaymentMethodApi {
    return PaymentMethodApi(
        cardDetails = cardDetails?.toApi(),
//        cardPresentDetails
        customer = customer,
        id = id,
        // interacPresentDetails
        livemode = livemode,
        metadata = metadata?.toHashMap() ?: hashMapOf(),
    )
}

fun CardDetails.toApi(): CardDetailsApi {
    return CardDetailsApi(
        brand = when (brand) {
            "amex" -> CardBrandApi.AMEX
            "diners" -> CardBrandApi.DINERS_CLUB
            "discover" -> CardBrandApi.DISCOVER
            "jcb" -> CardBrandApi.JCB
            "mastercard" -> CardBrandApi.MASTER_CARD
            "unionpay" -> CardBrandApi.UNION_PAY
            "visa" -> CardBrandApi.VISA
            "unknown" -> null
            else -> null
        },
        country = country,
        expMonth = expMonth.toLong(),
        expYear = expYear.toLong(),
        fingerprint = fingerprint,
        funding = when (brand) {
            "credit" -> CardFundingTypeApi.CREDIT
            "debit" -> CardFundingTypeApi.DEBIT
            "prepaid" -> CardFundingTypeApi.PREPAID
            "unknown" -> null
            else -> null
        },
//        generatedFrom
        last4 = last4,
    )
}

fun PaymentIntent.toApi(): PaymentIntentApi {
    return PaymentIntentApi(
        amount = amount.toDouble(),
        amountCapturable = amountCapturable.toDouble(),
//         amountDetails
        amountReceived = amountReceived.toDouble(),
//         amountTip
        application = application,
        applicationFeeAmount = applicationFeeAmount.toDouble(),
        canceledAt = canceledAt,
        cancellationReason = cancellationReason,
        captureMethod = captureMethod,
        clientSecret = clientSecret,
        confirmationMethod = confirmationMethod,
        created = created,
        currency = currency,
        description = description,
        id = id,
        invoice = invoice,
//         lastPaymentError
        livemode = livemode,
        metadata = metadata?.toHashMap() ?: hashMapOf(),
//         offlineBehavior
        onBehalfOf = onBehalfOf,
//         paymentMethod
//         paymentMethodData
        paymentMethodId = paymentMethodId,
//         paymentMethodOptions
//         paymentMethodUnion
        receiptEmail = receiptEmail,
        review = review,
        setupFutureUsage = setupFutureUsage,
//         statementDescriptor
//         statementDescriptorSuffix
        status = status?.toApi(),
//        stripeAccountId
        transferGroup = transferGroup,
        customer = customer,
    )
}

fun PaymentIntentStatus.toApi(): PaymentIntentStatusApi {
    return when (this) {
        PaymentIntentStatus.CANCELED -> PaymentIntentStatusApi.CANCELED
        PaymentIntentStatus.PROCESSING -> PaymentIntentStatusApi.PROCESSING
        PaymentIntentStatus.REQUIRES_CAPTURE -> PaymentIntentStatusApi.REQUIRES_CAPTURE
        PaymentIntentStatus.REQUIRES_CONFIRMATION -> PaymentIntentStatusApi.REQUIRES_CONFIRMATION
        PaymentIntentStatus.REQUIRES_PAYMENT_METHOD -> PaymentIntentStatusApi.REQUIRES_PAYMENT_METHOD
        PaymentIntentStatus.SUCCEEDED -> PaymentIntentStatusApi.SUCCEEDED
    }
}

fun Location.toApi(): LocationApi {
    return LocationApi(
        address = address?.toApi(),
        displayName = displayName,
        id = id,
        livemode = livemode,
        metadata = metadata?.toHashMap() ?: hashMapOf(),
    )
}

fun Address.toApi(): AddressApi {
    return AddressApi(
        city = city,
        country = country,
        line1 = line1,
        line2 = line2,
        postalCode = postalCode,
        state = state,
    )
}

fun PaymentStatus.toApi(): PaymentStatusApi {
    return when (this) {
        PaymentStatus.NOT_READY -> PaymentStatusApi.NOT_READY
        PaymentStatus.READY -> PaymentStatusApi.READY
        PaymentStatus.WAITING_FOR_INPUT -> PaymentStatusApi.WAITING_FOR_INPUT
        PaymentStatus.PROCESSING -> PaymentStatusApi.PROCESSING
    }
}
