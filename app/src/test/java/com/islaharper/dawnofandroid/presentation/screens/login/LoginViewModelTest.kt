package com.islaharper.dawnofandroid.presentation.screens.login

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.islaharper.dawnofandroid.domain.model.ApiResponse
import com.islaharper.dawnofandroid.domain.model.ApiTokenRequest
import com.islaharper.dawnofandroid.domain.model.MessageBarState
import com.islaharper.dawnofandroid.domain.useCases.readSignedInState.FakeReadSignInStateUseCase
import com.islaharper.dawnofandroid.domain.useCases.saveSignedInState.FakeSaveSignedInStateUseCase
import com.islaharper.dawnofandroid.domain.useCases.verifyToken.FakeVerifyTokenUseCase
import com.islaharper.dawnofandroid.util.Resource
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.net.ConnectException

class LoginViewModelTest {

    private lateinit var viewModel: LoginViewModel
    private lateinit var fakeReadSignedInStateUseCase: FakeReadSignInStateUseCase
    private lateinit var fakeSaveSignedInStateUseCase: FakeSaveSignedInStateUseCase
    private lateinit var fakeVerifyTokenUseCase: FakeVerifyTokenUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        fakeReadSignedInStateUseCase = FakeReadSignInStateUseCase()
        fakeSaveSignedInStateUseCase = FakeSaveSignedInStateUseCase()
        fakeVerifyTokenUseCase = FakeVerifyTokenUseCase()
        viewModel = LoginViewModel(
            readSignedInStateUseCase = fakeReadSignedInStateUseCase,
            saveSignedInStateUseCase = fakeSaveSignedInStateUseCase,
            verifyTokenUseCase = fakeVerifyTokenUseCase,
        )
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `for SaveSignedInStateUseCase, test if saveSignedInState() update signedInState value`() =
        runTest {
            assertEquals(false, viewModel.signedInState.value)
            viewModel.saveSignedInState(true)
            fakeSaveSignedInStateUseCase.emit(true)

            viewModel.signedInState.test {
                val emission = awaitItem()
                assertThat(emission).isEqualTo(true)
            }
        }

    @Test
    fun `for VerifyTokenUseCase, test if apiResponse default state is Idle`() = runTest {
        assertThat(viewModel.apiResponse.value).isEqualTo(Resource.Idle)
    }

    @Test
    fun `for VerifyTokenUseCase, test if verifyToken() updates apiResponse to Resource Success`() =
        runTest {
            assertThat(viewModel.apiResponse.value).isEqualTo(Resource.Idle)

            viewModel.verifyToken(
                ApiTokenRequest(tokenId = "TEST TOKEN"),
            )
            fakeVerifyTokenUseCase.emit(Resource.Success(ApiResponse(success = true)))
            viewModel.apiResponse.test {
                val emission = awaitItem()
                assertThat(emission).isEqualTo(Resource.Success(ApiResponse(success = true)))
            }
        }

    @Test
    fun `for VerifyTokenUseCase, test if verifyToken() updates messageBarState Success`() =
        runTest {
            assertThat(viewModel.apiResponse.value).isEqualTo(Resource.Idle)
            assertThat(viewModel.messageBarState.value).isEqualTo(MessageBarState.Idle)

            viewModel.verifyToken(ApiTokenRequest(tokenId = "TEST TOKEN"))
            fakeVerifyTokenUseCase.emit(Resource.Success(ApiResponse(success = true)))
            viewModel.messageBarState.test {
                val emission = awaitItem()
                assertThat(emission).isEqualTo(MessageBarState.Success(message = "Successfully Signed In"))
            }
        }

    @Test
    fun `for VerifyTokenUseCase, test if verifyToken() updates messageBarState Failure`() =
        runTest {
            assertThat(viewModel.apiResponse.value).isEqualTo(Resource.Idle)
            assertThat(viewModel.messageBarState.value).isEqualTo(MessageBarState.Idle)

            viewModel.verifyToken(ApiTokenRequest(tokenId = "TEST TOKEN"))
            fakeVerifyTokenUseCase.emit(Resource.Error(ex = ConnectException()))
            viewModel.messageBarState.test {
                val emission = awaitItem()
                assertThat(emission).isEqualTo(MessageBarState.Failure(message = "Internet Connection Unavailable"))
            }
        }
}
