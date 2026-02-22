package impl
import main.Echo

class DoubleEcho extends main.Echo:
  def echo(msg: String) = msg + " " + msg
end DoubleEcho
