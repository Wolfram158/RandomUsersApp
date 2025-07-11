package ru.nda.users.presentation.user

import android.R.drawable.stat_notify_error
import android.os.Build
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.buildSpannedString
import androidx.fragment.app.Fragment
import coil.ImageLoader
import coil.load
import coil.transform.CircleCropTransformation
import ru.nda.users.databinding.FragmentUserBinding
import ru.nda.users.domain.entity.User
import ru.nda.users.presentation.App
import javax.inject.Inject

fun SpannableStringBuilder.appendLine(header: String, args: List<Any>, separator: String = " ") {
    append("$header: ${args.joinToString(separator = separator)}${System.lineSeparator()}")
}

class UserFragment : Fragment() {
    private var _binding: FragmentUserBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentUserBinding is null")

    @Inject
    lateinit var imageLoader: ImageLoader

    private var _user: User? = null
    private val user
        get() = _user ?: throw RuntimeException("User is null")

    private val component by lazy {
        (requireActivity().application as App).component
    }

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
        arguments?.let { bundle ->
            _user = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getParcelable(USER, User::class.java)
            } else {
                bundle.getParcelable(USER)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUserInformation()
    }

    private fun setupUserInformation() {
        with(binding) {
            photoIv.load(user.picture.medium, imageLoader) {
                error(stat_notify_error)
                transformations(CircleCropTransformation())
            }
            commonInformationTv.text = buildSpannedString {
                appendLine("Name", listOf(user.name.firstName, user.name.lastName))
                appendLine("Phone", listOf(user.phone))
                appendLine("Gender", listOf(user.gender))
                appendLine("Date of birth", listOf(user.dob.date))
                appendLine("Age", listOf(user.dob.age))
                appendLine("Email", listOf(user.email))
            }
            loginTv.text = buildSpannedString {
                appendLine("Username", listOf(user.login.username))
                appendLine("Password", listOf(user.login.password))
            }
            locationTv.text = buildSpannedString {
                appendLine(
                    "Street",
                    listOf(user.location.street.name, user.location.street.number),
                    separator = ", "
                )
                appendLine("City", listOf(user.location.city))
                appendLine("State", listOf(user.location.state))
                appendLine("Country", listOf(user.location.country))
                appendLine("Postcode", listOf(user.location.postcode))
                appendLine(
                    "Coordinates",
                    listOf("(${user.location.coordinates.latitude}, ${user.location.coordinates.longitude})")
                )
                appendLine(
                    "Timezone",
                    listOf(user.location.timezone.description, user.location.timezone.offset),
                    separator = ", "
                )
            }
            registeredTv.text = buildSpannedString {
                appendLine("Date", listOf(user.registered.date))
                appendLine("Age", listOf(user.registered.age))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        //        private const val ITEM_CLASS_NAME = "ru.nda.users.domain.entity.User"
        private const val USER = "user"

        fun newInstance(user: User) =
            UserFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(USER, user)
//                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
//                        classLoader =
//                            ClassLoader.getSystemClassLoader()
//                                .loadClass(ITEM_CLASS_NAME).classLoader
//                    }
                }
            }
    }
}