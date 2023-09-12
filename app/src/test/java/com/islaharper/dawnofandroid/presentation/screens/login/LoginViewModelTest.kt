package com.islaharper.dawnofandroid.presentation.screens.login

import android.content.Intent
import app.cash.turbine.test
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.common.truth.Truth.assertThat
import com.islaharper.dawnofandroid.domain.model.ApiResponse
import com.islaharper.dawnofandroid.domain.model.MessageBarState
import com.islaharper.dawnofandroid.domain.useCases.signInClient.FakeSignInClientUseCase
import com.islaharper.dawnofandroid.domain.useCases.verifyToken.FakeVerifyTokenUseCase
import com.islaharper.dawnofandroid.util.Resource
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.net.ConnectException

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {
    private val mockSignInClient: SignInClient = mockk()
    private lateinit var viewModel: LoginViewModel
    private lateinit var fakeVerifyTokenUseCase: FakeVerifyTokenUseCase
    private lateinit var fakeSignInClientUseCase: FakeSignInClientUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())

        fakeVerifyTokenUseCase = FakeVerifyTokenUseCase()
        fakeSignInClientUseCase = FakeSignInClientUseCase()
        viewModel = LoginViewModel(
            oneTapClient = mockSignInClient,
            verifyTokenUseCase = fakeVerifyTokenUseCase,
            signInClientUseCase = fakeSignInClientUseCase
        )
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `for onGoogleButtonClick success, test loading and launchSignIn values update`() = runTest {
        val mockBeginSignInResult: BeginSignInResult = mockk(relaxed = true)

        assertThat(viewModel.loading.value).isEqualTo(false)
        assertThat(viewModel.launchSignIn.value).isEqualTo(null)

        viewModel.onGoogleButtonClick()
        fakeSignInClientUseCase.emit(Resource.Success(data = mockBeginSignInResult))

        viewModel.loading.test {
            val emission = awaitItem()
            assertThat(emission).isEqualTo(true)
        }

        viewModel.launchSignIn.test {
            val emission = awaitItem()
            assertThat(emission).isEqualTo(mockBeginSignInResult)
        }
    }

    @Test
    fun `for onGoogleButtonClick failure, test loading, launchSignIn and messageBarState values update`() =
        runTest {
            assertThat(viewModel.loading.value).isEqualTo(false)
            assertThat(viewModel.launchSignIn.value).isEqualTo(null)
            assertThat(viewModel.messageBarState.value).isEqualTo(MessageBarState.Idle)

            viewModel.onGoogleButtonClick()

            viewModel.loading.test {
                val emission = awaitItem()
                assertThat(emission).isEqualTo(true)
            }
            fakeSignInClientUseCase.emit(Resource.Error(ex = Exception()))

            viewModel.loading.test {
                val emission = awaitItem()
                assertThat(emission).isEqualTo(false)
            }

            viewModel.launchSignIn.test {
                val emission = awaitItem()
                assertThat(emission).isEqualTo(null)
            }

            viewModel.messageBarState.test {
                val emission = awaitItem()
                assertThat(emission).isEqualTo(MessageBarState.Failure(message = "Error Signing In"))
            }
        }

    @Test
    fun `for onOneTapSignInSuccess test if successful verifyTokenUseCase updates messageBarState, launchSignIn and navigationState`() =
        runTest {
            val fakeTokenId = "fakeTokenId"
            val fakeIntent: Intent = mockk(relaxed = true)
            val mockCredentials = mockk<SignInCredential> {
                every {
                    googleIdToken
                } returns fakeTokenId
            }

            assertThat(viewModel.messageBarState.value).isEqualTo(MessageBarState.Idle)
            assertThat(viewModel.navigationState.value).isEqualTo(false)

            every {
                mockSignInClient.getSignInCredentialFromIntent(fakeIntent)
            } returns mockCredentials

            viewModel.onOneTapSignInSuccess(fakeIntent)
            fakeVerifyTokenUseCase.emit(Resource.Success(ApiResponse(success = true)))

            viewModel.messageBarState.test {
                val emission = awaitItem()
                assertThat(emission).isEqualTo(MessageBarState.Success(message = "Successfully Signed In"))
            }

            viewModel.launchSignIn.test {
                val emission = awaitItem()
                assertThat(emission).isEqualTo(null)
            }

            viewModel.navigationState.test {
                val emission = awaitItem()
                assertThat(emission).isEqualTo(true)
            }
        }

    @Test
    fun `for onOneTapSignInSuccess test if unsuccessful verifyTokenUseCase updates messageBarState, launchSignIn and navigationState`() =
        runTest {
            val fakeTokenId = "fakeTokenId"
            val fakeIntent: Intent = mockk(relaxed = true)
            val mockCredentials = mockk<SignInCredential> {
                every {
                    googleIdToken
                } returns fakeTokenId
            }

            assertThat(viewModel.messageBarState.value).isEqualTo(MessageBarState.Idle)
            assertThat(viewModel.navigationState.value).isEqualTo(false)

            every {
                mockSignInClient.getSignInCredentialFromIntent(fakeIntent)
            } returns mockCredentials

            viewModel.onOneTapSignInSuccess(fakeIntent)
            fakeVerifyTokenUseCase.emit(Resource.Error(ex = ConnectException()))

            viewModel.messageBarState.test {
                val emission = awaitItem()
                assertThat(emission).isEqualTo(MessageBarState.Failure(message = "Internet Connection Unavailable"))
            }

            viewModel.launchSignIn.test {
                val emission = awaitItem()
                assertThat(emission).isEqualTo(null)
            }

            viewModel.navigationState.test {
                val emission = awaitItem()
                assertThat(emission).isEqualTo(false)
            }
        }

    @Test
    fun `for onOneTapSignInFailure, test messageBar failure message and values setToInitialSignInState`() =
        runTest {
            viewModel.onOneTapSignInFailure("test failure")

            assertThat(viewModel.messageBarState.value).isEqualTo(MessageBarState.Failure(message = "test failure"))
            assertThat(viewModel.launchSignIn.value).isEqualTo(null)
            assertThat(viewModel.loading.value).isEqualTo(false)
            assertThat(viewModel.navigationState.value).isEqualTo(false)
        }
}
