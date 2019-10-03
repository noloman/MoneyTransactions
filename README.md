# MoneyTransactions Android app
[![Build Status](https://img.shields.io/endpoint.svg?url=https%3A%2F%2Factions-badge.atrox.dev%2Fatrox%2Fsync-dotenv%2Fbadge&style=flat)](https://actions-badge.atrox.dev/atrox/sync-dotenv/goto)

<img src="https://raw.github.com/noloman/MoneyTransactions/master/account_screen.png" width=360px /> 
<img src="https://raw.github.com/noloman/MoneyTransactions/master/transaction_screen.png" width=360px /> 

Things to notice in the project:

- The UI tests in the AccountFragmentTest don't work due to mocking problems:

		(E/TestRunner: java.lang.IllegalStateException: 		
		Could not initialize plugin: interface 		
		org.mockito.plugins.MockMaker (alternate: null))
- The tests of the `Repository` class could be better tested.
- The extension function or mapper from `AcountData` to `Account` takes a bit more than `50ms`. I couldn't come up with a better solution, so I decided to go for this one. The way this mapping function works is:
	- From the transactions in an `AccountData` I map them to a `Transaction` with a `beforeBalance` and `afterBalance	` of 0.
	- Then, those transactions inside the list, they are sorted by date (the date in the `Transaction` model is a `LocalDateTime` and it can easily compared and thus sorted).
	- And finally, I created a new extension function from a `List<Transaction>` called `map`, which given a `Transaction` from the list, an initial balance, and iterates through every transaction in the list, accumulating the initial balance in the `accumulator` property, the `balanceBefore` is assigned the value of the `accumulator` and the `balanceAfter` as the sum of the `accumulator` plus the amount of the transaction. Finally, once the `balanceBefore` and `balanceAfter` are initialized with the proper values, the current transaction is copied with the new `balanceBefore` and `balanceAfter	` values.
