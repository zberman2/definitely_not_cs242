# The Vertex class defines vertices in the Graph for CSAir's map
class Vertex
  # readers allow us to read, but not write to, metro and edges
  attr_reader :metro
  attr_reader :edges

  # constructor for a vertex
  # Each vertex is defined by a metro and a list of edges, each
  # of which determine the cities one can reach from this vertex
  # @param metro city located at this vertex
  def initialize(metro)
    @metro = metro
    # set up an empty adjacency list for this vertex's outgoing edges
    @edges = []
  end

  private
  # method which creates and returns an array containing the distances
  # of each of this vertex's outgoing edges
  # @return array of distances to all adjacent vertices
  def distances
    @edges.collect{|edge| edge.distance}
  end

  public
  # method which allows us to add edges to the adjacency list
  # @param destination edge containing a vertex and distance
  def add_destination(destination)
    @edges << destination
  end

  # method which determines the longest outgoing edge from
  # this vertex (i.e. the flight from this city with the greatest
  # distance)
  # @return edge corresponding to the longest flight from this vertex
  def longest_flight
    # find the largest distance in the array of edges
    max = distances.max
    @edges.each{|edge| return edge if edge.distance == max}
  end

  # method which determines the shortest outgoing edge from
  # this vertex (i.e. the flight from this city with the shortest
  # distance)
  # @return edge corresponding to the shortest flight from this vertex
  def shortest_flight
    # find the shortest distance in the distances array
    min = distances.min
    @edges.each{|edge| return edge if edge.distance == min}
  end
end