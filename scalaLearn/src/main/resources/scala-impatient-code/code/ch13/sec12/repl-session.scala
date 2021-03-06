// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

val iter = (1 to 10).sliding(3)

while (iter.hasNext)
  println(iter.next())

val iter = (1 to 10).sliding(3)

for (elem <- iter)
  println(elem)

val iter = (1 to 10).sliding(3)

println(iter.length)

println(iter.hasNext) // The iterator is now consumed

val iter = (1 to 10).sliding(3)

iter.toArray

iter.toIterable

