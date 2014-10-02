# The Edge class defines edges in the Graph for CSAir's map
class Edge
  # readers allow us to read, but not write to, vertex and distance
  attr_reader :vertex
  attr_reader :distance

  # constructor for an edge
  # edges in this class are directed, so they will contain a single
  # vertex and a distance, indicating its edge weight
  # @param vertex Vertex object that this edge leads to
  # @param distance number of kilometers to the target vertex
  def initialize(vertex, distance)
    @vertex = vertex
    @distance = distance
  end

  # method which returns information about this edge, including
  # its destination vertex and distance to that vertex in kilometers
  # @return string containing edge info
  def info
    # newline characters used for nice formatting in the console
    info = "\n\nto\n\n"

    # call the metro info method to see the city's information
    info << @vertex.metro.info
    info << "\n\nDistance: #{@distance} km"
  end

  # method which prints out an abridged version of the info method
  # this method simply puts the name of the city corresponding to the
  # vertex, and the distance to get there
  # @return string containing short edge info
  def info_short
    "#{@vertex.metro.name}, distance: #{@distance} km"
  end
end